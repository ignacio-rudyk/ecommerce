-- ============================================================
--  generic_ecommerce — INSERTs tablas estáticas
-- ============================================================

BEGIN;

-- ------------------------------------------------------------
-- user_state
-- ------------------------------------------------------------
INSERT INTO user_state (code, name, title, description) VALUES
    ('001',    'ACTIVO',  'Activo',    'El usuario está activo y puede operar con normalidad'),
    ('002',  'INACTIVO',  'Inactivo',  'El usuario está inactivo y no puede operar'),
    ('003', 'SUSPENDIDO', 'Suspendido','El usuario fue suspendido');

-- ------------------------------------------------------------
-- order_state
-- ------------------------------------------------------------
INSERT INTO order_state (code, name, title, description) VALUES
    ('001',   'CONFIRMADO',   'Confirmado',   'La orden fue confirmada'),
    ('002', 'EN_PROCESO', 'En Proceso',  'La orden está en proceso'),
    ('003',   'CANCELADO',   'Cancelado',    'La orden fue cancelada'),
    ('004',    'REEMBOLSADO',    'Reembolsado',  'La orden fue reembolsada');

-- ------------------------------------------------------------
-- payment_state
-- ------------------------------------------------------------
INSERT INTO payment_state (code, name, title, description) VALUES
    ('001', 'PENDIENTE',   'Pendiente',   'El pago está pendiente de procesamiento'),
    ('002', 'APROBADO',  'Aprobado',    'El pago fue aprobado exitosamente'),
    ('003', 'RECHAZADO',  'Rechazado',   'El pago fue rechazado'),
    ('004', 'REEMBOLSADO',  'Reembolsado', 'El pago fue reembolsado al cliente'),
    ('005', 'CANCELADO', 'Cancelado',   'El pago fue cancelado');

-- ------------------------------------------------------------
-- payment_method
-- ------------------------------------------------------------
INSERT INTO payment_method (code, name, title, description) VALUES
    ('001',   'TARJETA_DE_CREDITO',     'Tarjeta de Credito',  'Pago con tarjeta de crédito'),
    ('002',   'TARJETA_DE_DEBITO',      'Tarjeta de Debito',   'Pago con tarjeta de débito'),
    ('003',   'TRANSFERENCIA',   'Transferencia',       'Pago por transferencia bancaria');

-- ------------------------------------------------------------
-- role
-- ------------------------------------------------------------
INSERT INTO role (code, name, description) VALUES
    ('001', 'ADMIN', 'Administrador con acceso total al sistema'),
    ('002', 'USER',  'Usuario estándar del ecommerce');

-- ------------------------------------------------------------
-- category
-- ------------------------------------------------------------
INSERT INTO category (name) VALUES
    ('Electronics'),
    ('Clothing'),
    ('Home'),
    ('Sports'),
    ('Books'),
    ('Food');

COMMIT;