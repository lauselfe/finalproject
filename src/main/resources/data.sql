
-- Insertar usuarios
INSERT INTO users (name, password) VALUES 
('John Doe', 'password123'),
('Jane Smith', 'securepassword456'),
('Alice Johnson', 'alicepassword789'),
('Bob Brown', 'bobpassword012'),
('Charlie Davis', 'charliepassword345'),
('Dana Lee', 'dana123password'),
('Eva Adams', 'eva_password678'),
('Frank Wilson', 'frankpassword901');

-- Insertar productos
INSERT INTO products (name, stock, price) VALUES 
('UltraComfort Ergonomic Office Chair', 100, 89.99),
('4K Ultra HD Smart TV', 50, 499.99),
('Wireless Noise-Canceling Headphones', 75, 129.99),
('Portable Bluetooth Speaker', 200, 29.99),
('Stainless Steel Coffee Maker', 150, 89.99),
('High-Speed Blender', 80, 79.99),
('Gaming Keyboard with Backlight', 60, 59.99),
('Smartphone with 5G', 120, 699.99),
('Digital Camera with 4K Video', 40, 899.99),
('Electric Toothbrush with UV Sanitizer', 90, 39.99),
('Memory Foam Mattress', 30, 349.99),
('Smart Home Thermostat', 110, 149.99),
('Fitness Tracker with Heart Rate Monitor', 85, 59.99),
('Electric Scooter', 70, 299.99),
('Air Fryer with Digital Control', 65, 119.99),
('Robot Vacuum Cleaner', 55, 229.99);

-- Insertar órdenes
INSERT INTO orders (name, date, user_id) VALUES 
('Electronics Bundle', '2024-09-01', 1),
('Office Upgrade', '2024-09-02', 2),
('Kitchen Essentials', '2024-09-03', 3),
('Fitness and Health', '2024-09-04', 4),
('Gamer’s Dream', '2024-09-05', 5),
('Home Automation', '2024-09-06', 6),
('Travel Gear', '2024-09-07', 7),
('Back to School Supplies', '2024-09-08', 8);

-- Insertar items de la orden
-- Para Electronics Bundle
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(1, 2, 1), -- 1 unidad de 4K Ultra HD Smart TV en Electronics Bundle
(1, 3, 2), -- 2 unidades de Wireless Noise-Canceling Headphones en Electronics Bundle
(1, 4, 1); -- 1 unidad de Portable Bluetooth Speaker en Electronics Bundle

-- Para Office Upgrade
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(2, 1, 1), -- 1 unidad de UltraComfort Ergonomic Office Chair en Office Upgrade
(2, 7, 1); -- 1 unidad de Gaming Keyboard with Backlight en Office Upgrade

-- Para Kitchen Essentials
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(3, 5, 1), -- 1 unidad de Stainless Steel Coffee Maker en Kitchen Essentials
(3, 6, 1), -- 1 unidad de High-Speed Blender en Kitchen Essentials
(3, 14, 1); -- 1 unidad de Air Fryer with Digital Control en Kitchen Essentials

-- Para Fitness and Health
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(4, 12, 1), -- 1 unidad de Smart Home Thermostat en Fitness and Health
(4, 13, 2), -- 2 unidades de Fitness Tracker with Heart Rate Monitor en Fitness and Health
(4, 15, 1); -- 1 unidad de Electric Scooter en Fitness and Health

-- Para Gamer’s Dream
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(5, 7, 1), -- 1 unidad de Gaming Keyboard with Backlight en Gamer’s Dream
(5, 8, 1), -- 1 unidad de Smartphone with 5G en Gamer’s Dream
(5, 9, 1); -- 1 unidad de Digital Camera with 4K Video en Gamer’s Dream

-- Para Home Automation
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(6, 12, 1), -- 1 unidad de Smart Home Thermostat en Home Automation
(6, 14, 1); -- 1 unidad de Air Fryer with Digital Control en Home Automation

-- Para Travel Gear
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(7, 15, 1), -- 1 unidad de Electric Scooter en Travel Gear
(7, 4, 1); -- 1 unidad de Portable Bluetooth Speaker en Travel Gear

-- Para Back to School Supplies
INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(8, 1, 1), -- 1 unidad de UltraComfort Ergonomic Office Chair en Back to School Supplies
(8, 10, 2), -- 2 unidades de Electric Toothbrush with UV Sanitizer en Back to School Supplies
(8, 11, 1); -- 1 unidad de Memory Foam Mattress en Back to School Supplies

