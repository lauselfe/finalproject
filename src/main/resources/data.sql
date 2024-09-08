-- Inserción de datos en la tabla 'users'
INSERT INTO users (id, money_expended, name, password) VALUES (1, 0.0, 'Juan', 'password123');
INSERT INTO users (id, money_expended, name, password) VALUES (2, 100.0, 'Maria', 'password456');

-- Inserción de datos en la tabla 'products'
INSERT INTO products (id, name, price, sales, stock) VALUES (1, 'Producto A', 10.0, 0, 100);
INSERT INTO products (id, name, price, sales, stock) VALUES (2, 'Producto B', 20.0, 0, 50);

-- Inserción de datos en la tabla 'orders'
INSERT INTO orders (id, date, name, total, user_id) VALUES (1, '2023-09-01', 'Pedido 1', 0.0, 1);
INSERT INTO orders (id, date, name, total, user_id) VALUES (2, '2023-09-02', 'Pedido 2', 0.0, 2);

-- Inserción de datos en la tabla 'order_items'
INSERT INTO order_items (id, quantity, order_id, product_id) VALUES (1, 2, 1, 1); -- Pedido 1 tiene 2 unidades de Producto A
INSERT INTO order_items (id, quantity, order_id, product_id) VALUES (2, 3, 2, 2); -- Pedido 2 tiene 3 unidades de Producto B

-- Actualización de los totales en 'orders'
UPDATE orders SET total = (SELECT SUM(p.price * oi.quantity) FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = orders.id);

-- Actualización de las ventas de los productos
UPDATE products SET sales = (SELECT SUM(oi.quantity) FROM order_items oi WHERE oi.product_id = products.id);

-- Actualización del dinero gastado por los usuarios
UPDATE users SET money_expended = (SELECT SUM(o.total) FROM orders o WHERE o.user_id = users.id);

