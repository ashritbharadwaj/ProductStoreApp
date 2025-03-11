CREATE TABLE IF NOT EXISTS `order` (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    user_email VARCHAR(255) NOT NULL  -- New column
);