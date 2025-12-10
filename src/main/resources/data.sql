-- 1. Insertar cupones primero (IDs fijos: 1, 2, 3)
INSERT INTO coupon (id, code, description, discount_type, discount_value, active, valid_from, valid_to)
VALUES
    (1, 'DISCOUNT10', '10% discount on total', 'PERCENT', 10.00, TRUE, '2025-01-01', '2025-12-31'),
    (2, 'MINUS5', '5 EUR discount on total', 'FIXED_AMOUNT', 5.00, TRUE, '2025-01-01', '2025-12-31'),
    (3, 'SUMMER20', '20% summer discount', 'PERCENT', 20.00, TRUE, '2025-06-01', '2025-08-31');

-- 2. Insertar productos (IDs fijos: 1..5)
INSERT INTO product (id, name, description, price, active)
VALUES
    (1, 'Surf Board', 'Professional surf board for waves', 250.00, TRUE),
    (2, 'Diving Kit', 'Complete diving kit with mask and fins', 120.00, TRUE),
    (3, 'Camping Tent', '4-person waterproof tent', 180.00, TRUE),
    (4, 'Wakeboard', 'Wakeboard for water sports', 200.00, TRUE),
    (5, 'Concert Ticket', 'Ticket for summer beach concert', 50.00, TRUE);

-- 3. Insertar pedidos (IDs fijos: 1, 2) que referencian cupones 1 y 2
INSERT INTO customer_order (id, order_number, created_at, status, gross_total, discount_total, final_total,
                            coupon_id, payment_method, payment_status, payment_details,
                            billing_name, billing_tax_id, billing_street, billing_city, billing_postal_code, billing_country,
                            shipping_name, shipping_street, shipping_city, shipping_postal_code, shipping_country)
VALUES
    (1, 'ORD001', NOW(), 'PAID', 370.00, 37.00, 333.00,
     1, 'CARD', 'PAID', 'Visa ****1234',
     'Iván Pérez', 'ES12345678', 'Calle Sol 12', 'Málaga', '29001', 'España',
     'Iván Pérez', 'Calle Sol 12', 'Málaga', '29001', 'España'),

    (2, 'ORD002', NOW(), 'NEW', 200.00, 5.00, 195.00,
     2, 'PAYPAL', 'PENDING', 'PayPal user ivan@example.com',
     'Laura Gómez', NULL, 'Av. Costa 45', 'Marbella', '29600', 'España',
     'Laura Gómez', 'Av. Costa 45', 'Marbella', '29600', 'España');

-- 4. Insertar ítems de pedido (referencian order_id y product_id válidos)
INSERT INTO order_item (id, order_id, product_id, product_name, unit_price, quantity, line_total)
VALUES
    (1, 1, 1, 'Surf Board', 250.00, 1, 250.00),
    (2, 1, 2, 'Diving Kit', 120.00, 1, 120.00),
    (3, 2, 5, 'Concert Ticket', 50.00, 4, 200.00);