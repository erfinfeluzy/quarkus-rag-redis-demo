-- Dijalankan otomatis oleh container MySQL saat pertama kali dibuat (volume masih kosong).
CREATE TABLE IF NOT EXISTS employees (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    department  VARCHAR(50)   NOT NULL,
    position    VARCHAR(100)  NOT NULL,
    office      VARCHAR(50)   NOT NULL,
    salary      DECIMAL(15,2) NOT NULL COMMENT 'Gaji pokok bulanan (Rupiah)',
    join_date   DATE          NOT NULL
);

INSERT INTO employees (name, department, position, office, salary, join_date) VALUES
('Budi Santoso',     'Engineering', 'Backend Engineer',        'Bandung',    14500000, '2021-03-15'),
('Siti Rahmawati',   'Engineering', 'Engineering Manager',     'Bandung',    28000000, '2018-07-01'),
('Andi Pratama',     'Engineering', 'Frontend Engineer',       'Yogyakarta', 12000000, '2022-09-12'),
('Dewi Lestari',     'Engineering', 'QA Engineer',             'Malang',      9500000, '2023-02-20'),
('Rizky Hidayat',    'Marketing',   'Marketing Manager',       'Bandung',    22000000, '2019-05-06'),
('Putri Ayu',        'Marketing',   'Social Media Specialist', 'Yogyakarta',  8000000, '2023-08-01'),
('Agus Setiawan',    'Sales',       'Sales Executive',         'Malang',      7500000, '2022-01-10'),
('Maya Sari',        'Sales',       'Sales Manager',           'Bandung',    20000000, '2017-11-13'),
('Hendra Wijaya',    'Finance',     'Finance Manager',         'Bandung',    25000000, '2016-04-04'),
('Rina Kusuma',      'Finance',     'Accountant',              'Bandung',    11000000, '2020-10-19'),
('Fajar Nugroho',    'Operations',  'Head Roaster',            'Bandung',    13000000, '2019-02-25'),
('Lina Marlina',     'Operations',  'Barista Trainer',         'Yogyakarta',  6500000, '2024-03-04'),
('Yoga Permana',     'Operations',  'Warehouse Staff',         'Malang',      5500000, '2024-06-17'),
('Nadia Putri',      'HR',          'HR Manager',              'Bandung',    21000000, '2018-01-08'),
('Dimas Saputra',    'HR',          'Recruiter',               'Bandung',     9000000, '2022-11-21');
