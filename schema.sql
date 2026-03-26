CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(50),
    password VARCHAR(250),
    type ENUM ('KH_USER') NOT NULL,
    is_active ENUM ('T', 'F') DEFAULT 'F' NOT NULL,
    two_fa_enabled ENUM ('T', 'F') DEFAULT 'F' NOT NULL,
    two_fa_secret VARCHAR(250),
    two_fa_secret_temp VARCHAR(250)
);
