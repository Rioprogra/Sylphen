-- ============================================================
--  SYLPHEN — Base de datos MySQL
--  Basado en el Modelo Entidad-Relación y Modelo Relacional
--  Compatibilidad: MySQL 8.0+
--  Ejecutar con: mysql -u root -p < sylphen_db.sql
-- ============================================================


-- ---- 1. CREAR Y SELECCIONAR LA BASE DE DATOS ---------------
CREATE DATABASE IF NOT EXISTS sylphen_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sylphen_db;


-- ============================================================
--  TABLA 1: USUARIO
--  Un usuario puede realizar muchos logins (1:N con HISTORIAL_LOGIN)
-- ============================================================
CREATE TABLE IF NOT EXISTS USUARIO (
    id_usuario     INT          NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(100) NOT NULL             COMMENT 'Nombre completo del usuario',
    usuario        VARCHAR(50)  NOT NULL             COMMENT 'Nombre de usuario para login',
    correo         VARCHAR(100) NOT NULL             COMMENT 'Correo electronico',
    password       VARCHAR(255) NOT NULL             COMMENT 'Contrasena (Fase 2: hash bcrypt)',
    rol            VARCHAR(20)  NOT NULL
                   DEFAULT 'USUARIO'                COMMENT 'USUARIO o ADMINISTRADOR',
    fecha_registro DATETIME     DEFAULT CURRENT_TIMESTAMP
                                                     COMMENT 'Fecha de creacion de la cuenta',

    PRIMARY KEY (id_usuario),
    UNIQUE  KEY uq_usuario (usuario),
    UNIQUE  KEY uq_correo  (correo)
);


-- ============================================================
--  TABLA 2: HISTORIAL_LOGIN
--  Registra cada inicio de sesion exitoso.
--  Relacion: USUARIO (1) → HISTORIAL_LOGIN (N)
-- ============================================================
CREATE TABLE IF NOT EXISTS HISTORIAL_LOGIN (
    id_login   INT      NOT NULL AUTO_INCREMENT,
    id_usuario INT      NOT NULL                     COMMENT 'FK a USUARIO',
    fecha_hora DATETIME NOT NULL
               DEFAULT CURRENT_TIMESTAMP             COMMENT 'Fecha y hora del login',

    PRIMARY KEY (id_login),
    CONSTRAINT fk_login_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES USUARIO (id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ============================================================
--  TABLA 3: DISPOSITIVO
--  Los 4 componentes fisicos del sistema Sylphen.
--  Relacion: DISPOSITIVO (1) → LECTURA_ENERGIA (N)
-- ============================================================
CREATE TABLE IF NOT EXISTS DISPOSITIVO (
    id_dispositivo INT         NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(50) NOT NULL               COMMENT 'Nombre del componente',
    tipo           VARCHAR(50) NOT NULL               COMMENT 'Categoria del dispositivo',
    estado         VARCHAR(20) NOT NULL
                   DEFAULT 'OFFLINE'                  COMMENT 'ONLINE / OFFLINE / ERROR',
    ultima_lectura DATETIME                           COMMENT 'Ultimo dato recibido',

    PRIMARY KEY (id_dispositivo)
);


-- ============================================================
--  TABLA 4: LECTURA_ENERGIA
--  Cada registro = una medicion del sensor INA226.
--  El ESP32 inserta una fila aqui cada vez que envia datos.
-- ============================================================
CREATE TABLE IF NOT EXISTS LECTURA_ENERGIA (
    id_lectura         INT          NOT NULL AUTO_INCREMENT,
    fecha_hora         DATETIME     NOT NULL
                       DEFAULT CURRENT_TIMESTAMP       COMMENT 'Timestamp de la medicion',
    voltaje            DECIMAL(6,2) NOT NULL            COMMENT 'Voltaje en V (ej: 3.89)',
    corriente          DECIMAL(6,2) NOT NULL            COMMENT 'Corriente en A (ej: 1.24)',
    potencia           DECIMAL(6,2) NOT NULL            COMMENT 'Potencia en W = V x I',
    bateria_porcentaje DECIMAL(5,2) NOT NULL            COMMENT 'Nivel de bateria 0-100 (%)',

    PRIMARY KEY (id_lectura),
    INDEX idx_lectura_fecha (fecha_hora)               -- acelera consultas de historial
);


-- ============================================================
--  DATOS INICIALES
-- ============================================================

-- Los 4 dispositivos fisicos del hardware Sylphen
-- Estos registros son fijos. Solo se actualiza su estado y ultima_lectura.
INSERT INTO DISPOSITIVO (nombre, tipo, estado) VALUES
    ('Arduino Uno',    'microcontrolador', 'OFFLINE'),
    ('ESP32',          'comunicacion',     'OFFLINE'),
    ('INA226',         'sensor',           'OFFLINE'),
    ('Batería 18650',  'almacenamiento',   'OFFLINE');

-- Usuario administrador por defecto
-- IMPORTANTE: En Fase 2 cambiar el password por un hash BCrypt real
INSERT INTO USUARIO (nombre, usuario, correo, password, rol) VALUES
    ('Administrador Sylphen', 'admin', 'admin@sylphen.com', 'admin123', 'ADMINISTRADOR');


-- ============================================================
--  VERIFICACION FINAL — ejecuta esto para confirmar que todo se creo bien
-- ============================================================
SELECT 'Tablas creadas:' AS '';
SHOW TABLES;

SELECT '' AS '';
SELECT 'Dispositivos iniciales:' AS '';
SELECT * FROM DISPOSITIVO;

SELECT '' AS '';
SELECT 'Usuario admin:' AS '';
SELECT id_usuario, nombre, usuario, rol, fecha_registro FROM USUARIO;
