-- INVENTORY MANAGEMENT SYSTEM

CREATE DATABASE inventory_management;

USE inventory_management;

-- SUPPLIERS TABLE

CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
);

-- PRODUCTS TABLE

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100),
    price DECIMAL(10,2),
    quantity INT,
    supplier_id INT,

    FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id)
);

-- SALES TABLE

CREATE TABLE sales (
    sale_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    quantity_sold INT,
    total_amount DECIMAL(10,2),
    sale_date DATE,

    FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);

-- SAMPLE SUPPLIER DATA

INSERT INTO suppliers
(supplier_name, email, phone)
VALUES
('ABC Electronics', 'abc@gmail.com', '9876543210'),
('XYZ Traders', 'xyz@gmail.com', '9876543211'),
('Global Suppliers', 'global@gmail.com', '9876543212');

-- SAMPLE PRODUCT DATA

INSERT INTO products
(product_name, price, quantity, supplier_id)
VALUES
('Laptop', 50000.00, 10, 1),
('Mouse', 800.00, 25, 2),
('Keyboard', 1500.00, 20, 2),
('Monitor', 12000.00, 8, 3),
('Printer', 15000.00, 5, 1);

-- SAMPLE SALES DATA

INSERT INTO sales
(product_id, quantity_sold, total_amount, sale_date)
VALUES
(1, 2, 100000.00, '2026-08-21'),
(2, 5, 4000.00, '2026-08-21'),
(3, 3, 4500.00, '2026-08-21'),
(4, 1, 12000.00, '2026-08-21');

-- VIEW TABLE DATA

SELECT * FROM suppliers;

SELECT * FROM products;

SELECT * FROM sales;