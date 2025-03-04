INSERT INTO user (nombre, email, password) 
VALUES 
('maria', 'maria@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('miguel', 'miguel@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('Matias', 'matias@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('mireia', 'mireia@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw==');

INSERT INTO cliente (nombre, email, password) 
VALUES 
('Luis', 'luis@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('Laura', 'laura@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('Lorenzo', 'lorenzo@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('Lorena', 'lorena@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw==');

INSERT INTO producto (nombre, precio)
VALUES ('Camiseta Blanca', 20.50),
('Camiseta Verde', 24.75),
('Camiseta Negra', 21.25),
('Camiseta Amarilla', 28.00);

INSERT INTO pedido (cliente_id, fecha) 
VALUES 
    (1, '2025-02-21 12:00:00'),
    (2, '2025-02-21 12:30:00'),
    (3, '2025-02-21 13:00:00'),
    (4, '2025-02-21 13:30:00'),
    (1, '2025-03-01 10:00:00'),
    (2, '2025-03-02 11:00:00'),
    (3, '2025-03-03 12:00:00'),
    (4, '2025-03-04 13:00:00'),
    (1, '2025-04-01 14:00:00'),
    (2, '2025-04-02 15:00:00'),
    (3, '2025-04-03 16:00:00'),
    (4, '2025-04-04 17:00:00'),
    (1, '2025-05-01 18:00:00'),
    (2, '2025-05-02 19:00:00'),
    (3, '2025-05-03 20:00:00'),
    (4, '2025-05-04 21:00:00'),
    (1, '2025-06-01 22:00:00'),
    (2, '2025-06-02 23:00:00'),
    (3, '2025-06-03 00:00:00'),
    (4, '2025-06-04 01:00:00');

INSERT INTO pedido_producto (pedido_id, producto_id, cantidad)
VALUES (1, 1, 4),
    (2, 2, 1),
    (2, 3, 2),
    (3, 1, 5),
    (3, 2, 5),
    (3, 3, 5),
    (4, 1, 20),
    (4, 2, 15),
    (4, 3, 12),
    (4, 4, 10),
    (5, 1, 3),
    (5, 2, 2),
    (6, 3, 4),
    (6, 4, 1),
    (7, 1, 5),
    (7, 2, 3),
    (8, 3, 2),
    (8, 4, 5),
    (9, 1, 1),
    (9, 2, 4),
    (10, 3, 3),
    (10, 4, 2),
    (11, 1, 2),
    (11, 2, 2),
    (12, 3, 5),
    (12, 4, 3),
    (13, 1, 4),
    (13, 2, 5),
    (14, 3, 3),
    (14, 4, 1);