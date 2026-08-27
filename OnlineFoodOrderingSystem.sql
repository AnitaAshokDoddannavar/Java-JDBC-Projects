-- ONLINE FOOD ORDERING SYSTEM
CREATE DATABASE food_ordering;

USE food_ordering;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE menu (
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    food_name VARCHAR(100),
    category VARCHAR(50),
    price DECIMAL(10,2),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE,
    total_amount DECIMAL(10,2),
    order_status VARCHAR(30) NOT NULL DEFAULT 'PLACED',

    FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    food_id INT,
    quantity INT,
    item_price DECIMAL(10,2),

    FOREIGN KEY (order_id)
    REFERENCES orders(order_id),

    FOREIGN KEY (food_id)
    REFERENCES menu(food_id)
);

INSERT INTO customers
(customer_name, email, phone)
VALUES
('Anita', 'anita@gmail.com', '9876543210'),
('Rahul', 'rahul@gmail.com', '9876543211'),
('Priya', 'priya@gmail.com', '9876543212');

SELECT * FROM customers;

INSERT INTO menu
(food_name, category, price)
VALUES
('Pizza', 'Main Course', 200.00),
('Burger', 'Main Course', 120.00),
('Coke', 'Beverage', 50.00),
('French Fries', 'Starter', 80.00),
('Ice Cream', 'Dessert', 100.00);

SELECT * FROM menu;

select * from orders;

SELECT * FROM order_items;



