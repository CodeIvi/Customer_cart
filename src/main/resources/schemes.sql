CREATE DATABASE IF NOT EXISTS shopping_cart;
USE shopping_cart;

-- Tabla de cupones
CREATE TABLE coupon (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        code VARCHAR(50) NOT NULL UNIQUE,
                        description TEXT,
                        discount_type ENUM('PERCENT', 'FIXED_AMOUNT') NOT NULL,
                        discount_value DECIMAL(10,2) NOT NULL,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        valid_from DATE DEFAULT NULL,
                        valid_to DATE DEFAULT NULL
);

-- Tabla de productos
CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(100),
                         price DECIMAL(10,2) NOT NULL,
                         active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla de pedidos
CREATE TABLE customer_order (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                order_number VARCHAR(50) NOT NULL UNIQUE,
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                status ENUM('NEW', 'PAID', 'FAILED', 'PENDING_PAYMENT') NOT NULL,
                                gross_total DECIMAL(10,2) NOT NULL,
                                discount_total DECIMAL(10,2) DEFAULT 0.00,
                                final_total DECIMAL(10,2) NOT NULL,
                                coupon_id INT DEFAULT NULL,
                                payment_method ENUM('CARD', 'PAYPAL', 'BANK_TRANSFER') NOT NULL,
                                payment_status ENUM('PENDING', 'PAID', 'FAILED') NOT NULL,
                                payment_details TEXT,

                                billing_name VARCHAR(100) NOT NULL,
                                billing_tax_id VARCHAR(50) DEFAULT NULL,
                                billing_street VARCHAR(150) NOT NULL,
                                billing_city VARCHAR(100) NOT NULL,
                                billing_postal_code VARCHAR(20) NOT NULL,
                                billing_country VARCHAR(100) NOT NULL,

                                shipping_name VARCHAR(100) NOT NULL,
                                shipping_street VARCHAR(150) NOT NULL,
                                shipping_city VARCHAR(100) NOT NULL,
                                shipping_postal_code VARCHAR(20) NOT NULL,
                                shipping_country VARCHAR(100) NOT NULL,

                                FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);

-- Tabla de ítems del pedido
CREATE TABLE order_item (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            order_id INT NOT NULL,
                            product_id INT DEFAULT NULL,
                            product_name VARCHAR(100) NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,
                            quantity INT NOT NULL,
                            line_total DECIMAL(10,2) NOT NULL,

                            FOREIGN KEY (order_id) REFERENCES customer_order(id),
                            FOREIGN KEY (product_id) REFERENCES product(id)
);
