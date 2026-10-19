package com.erfinfeluzy;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.sql.DataSource;

/**
 * Read-only tools the model can call to look up employee salaries in MySQL.
 * Every query is parameterized; the model never writes SQL itself.
 */
@ApplicationScoped
public class SalaryTools {

    private static final String COLUMNS =
            "name, department, position, office, salary AS monthly_salary_rupiah, join_date";

    @Inject
    DataSource dataSource;

    @Tool("Find employees by (part of) their name and return their department, position, office, "
            + "monthly salary in Rupiah and join date.")
    public List<Map<String, Object>> findEmployeeSalary(@P("Full or partial employee name") String name) {
        Log.infof("Tool findEmployeeSalary(name=%s)", name);
        return query("SELECT " + COLUMNS + " FROM employees WHERE name LIKE ? ORDER BY name", "%" + name + "%");
    }

    @Tool("List all employees of a department with their monthly salary in Rupiah. "
            + "Departments: Engineering, Marketing, Sales, Finance, Operations, HR.")
    public List<Map<String, Object>> listEmployeesByDepartment(@P("Department name") String department) {
        Log.infof("Tool listEmployeesByDepartment(department=%s)", department);
        return query("SELECT " + COLUMNS + " FROM employees WHERE department = ? ORDER BY salary DESC", department);
    }

    @Tool("Get salary statistics (employee count, total, average, minimum and maximum monthly salary in Rupiah) "
            + "per department, for the whole company.")
    public List<Map<String, Object>> salaryStatisticsPerDepartment() {
        Log.info("Tool salaryStatisticsPerDepartment()");
        return query("""
                SELECT department, COUNT(*) AS employee_count, SUM(salary) AS total_salary,
                       ROUND(AVG(salary)) AS average_salary, MIN(salary) AS min_salary, MAX(salary) AS max_salary
                FROM employees GROUP BY department ORDER BY department""");
    }

    private List<Map<String, Object>> query(String sql, Object... params) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = statement.executeQuery()) {
                List<Map<String, Object>> rows = new ArrayList<>();
                int columns = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int c = 1; c <= columns; c++) {
                        row.put(rs.getMetaData().getColumnLabel(c), String.valueOf(rs.getObject(c)));
                    }
                    rows.add(row);
                }
                return rows;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Database query failed: " + e.getMessage(), e);
        }
    }
}
