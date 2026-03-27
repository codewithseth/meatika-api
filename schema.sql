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

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(50) NOT NULL UNIQUE,
    alias VARCHAR(50) NOT NULL UNIQUE,
    name_en VARCHAR(50) NOT NULL,
    name_km VARCHAR(50) NOT NULL,
    description_en TEXT,
    description_km TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS permissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(50) NOT NULL UNIQUE,
    alias VARCHAR(50) NOT NULL UNIQUE,
    name_en VARCHAR(50) NOT NULL,
    name_km VARCHAR(50) NOT NULL,
    group_name_en VARCHAR(50) NOT NULL,
    group_name_km VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_uuid VARCHAR(50) NOT NULL,
    permission_uuid VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_uuid, permission_uuid),
    FOREIGN KEY (role_uuid) REFERENCES roles(uuid) ON DELETE CASCADE,
    FOREIGN KEY (permission_uuid) REFERENCES permissions(uuid) ON DELETE CASCADE
);