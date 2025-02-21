USE myappdb;

CREATE TABLE IF NOT EXISTS user (
    id_user INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS producto (
    id_producto INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedido (
    id_pedido INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id_cliente) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pedido_producto (
    id_pedido_producto INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    UNIQUE (pedido_id, producto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id_producto) ON DELETE CASCADE
);