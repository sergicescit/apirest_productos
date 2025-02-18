INSERT INTO user (nombre, email, password) 
VALUES 
('maria', 'maria@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('miguel', 'miguel@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('andres', 'andres@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw=='),
('mireia', 'mireia@mail.com', 'zIPSCkL6mo+xA/GNmSt8Rw==');

INSERT INTO producto (nombre, precio)
VALUES ('Camiseta Blanca', 20.50),
('Camiseta Verde', 24.75),
('Camiseta Negra', 21.25),
('Camiseta Amarilla', 28.00);

INSERT INTO pedido (cliente)
VALUES (1), (2), (3), (4);

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
(4, 4, 10);