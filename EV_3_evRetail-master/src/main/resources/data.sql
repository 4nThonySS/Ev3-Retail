/* =========================================================
   DATA SQL - EVARETAIL
   Base de datos: ORACLE
   Archivo:
   src/main/resources/data.sql
   ========================================================= */


/* =========================================================
   1. PRODUCTOS
   Tabla: producto
   ========================================================= */
INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Laptop Lenovo', 650000, 15);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Mouse Gamer', 25000, 50);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Teclado Mecanico', 55000, 30);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Monitor Samsung 24', 180000, 20);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Audifonos Bluetooth', 35000, 40);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Webcam Logitech', 45000, 18);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Disco SSD 1TB', 85000, 25);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Memoria RAM 16GB', 70000, 35);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Silla Gamer', 120000, 10);

INSERT INTO producto (id, nombre, precio, stock)
VALUES (SEQ_PRODUCTO.NEXTVAL, 'Tablet Samsung', 250000, 12);

COMMIT;
SELECT * FROM producto;



