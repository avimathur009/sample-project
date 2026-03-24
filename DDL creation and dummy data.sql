DDL — Create Tables

  -- 1. Cart (must come before app_user)
CREATE TABLE IF NOT EXISTS cart (
                                    cart_id BIGSERIAL PRIMARY KEY
);

-- 2. Cart items join table (Map<Product, Integer>)
CREATE TABLE IF NOT EXISTS cart_items (
                                          cart_id    BIGINT  NOT NULL REFERENCES cart(cart_id)    ON DELETE CASCADE,
    product_id BIGINT  NOT NULL REFERENCES product(product_id) ON DELETE CASCADE,
    quantity   INTEGER NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (cart_id, product_id)
    );

-- 3. Invoice
CREATE TABLE IF NOT EXISTS invoice (
                                       invoice_id     BIGSERIAL    PRIMARY KEY,
                                       invoice_number VARCHAR(50)  NOT NULL UNIQUE,
    total_amount   NUMERIC(19,2) NOT NULL,
    generated_at   TIMESTAMP    NOT NULL
    );

-- 4. Payment
CREATE TABLE IF NOT EXISTS payment (
                                       payment_id   BIGSERIAL    PRIMARY KEY,
                                       payment_type VARCHAR(50)  NOT NULL,
    amount       NUMERIC(19,2) NOT NULL,
    status       VARCHAR(50)  NOT NULL,
    paid_at      TIMESTAMP
    );

-- 5. App User (references cart)
CREATE TABLE IF NOT EXISTS app_user (
                                        user_id BIGSERIAL    PRIMARY KEY,
                                        name    VARCHAR(255) NOT NULL,
    cart_id BIGINT       UNIQUE REFERENCES cart(cart_id) ON DELETE SET NULL
    );

-- 6. Orders (references app_user, warehouse, payment, invoice)
CREATE TABLE IF NOT EXISTS orders (
                                      order_id    BIGSERIAL    PRIMARY KEY,
                                      order_ref   VARCHAR(50)  NOT NULL UNIQUE,
    del_street  VARCHAR(255),
    del_city    VARCHAR(100),
    del_state   VARCHAR(100),
    del_zip_code VARCHAR(20),
    del_country VARCHAR(100),
    warehouse_id BIGINT REFERENCES warehouse(warehouse_id)  ON DELETE SET NULL,
    user_id      BIGINT REFERENCES app_user(user_id)        ON DELETE SET NULL,
    payment_id   BIGINT UNIQUE REFERENCES payment(payment_id)  ON DELETE CASCADE,
    invoice_id   BIGINT UNIQUE REFERENCES invoice(invoice_id)  ON DELETE CASCADE,
    created_at   TIMESTAMP NOT NULL
    );

---
DML — Dummy Data

  -- ── Step 1: Carts (one per user) ──────────────────────────────────────────────
  INSERT INTO cart (cart_id) VALUES (1), (2), (3);

  -- ── Step 2: Users ─────────────────────────────────────────────────────────────
INSERT INTO app_user (user_id, name, cart_id) VALUES
                                                  (1, 'Alice Johnson', 1),
                                                  (2, 'Bob Smith',     2),
                                                  (3, 'Carol White',   3);

-- ── Step 3: Cart items (product IDs 1–12 seeded by InventoryDataInitializer) ──
-- Alice's cart: 2x Product 1, 1x Product 3
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
                                                           (1, 1, 2),
                                                           (1, 3, 1);

-- Bob's cart: 3x Product 5
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
    (2, 5, 3);

-- Carol's cart: 1x Product 7, 2x Product 9
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
                                                           (3, 7, 1),
                                                           (3, 9, 2);

-- ── Step 4: Payments ──────────────────────────────────────────────────────────
INSERT INTO payment (payment_id, payment_type, amount, status, paid_at) VALUES
                                                                            (1, 'CASH',        199.98, 'COMPLETED', NOW()),
                                                                            (2, 'CREDIT_CARD', 749.97, 'COMPLETED', NOW()),
                                                                            (3, 'CASH',        89.99,  'COMPLETED', NOW());

-- ── Step 5: Invoices ──────────────────────────────────────────────────────────
INSERT INTO invoice (invoice_id, invoice_number, total_amount, generated_at) VALUES
                                                                                 (1, 'INV-A1B2C3D4', 199.98, NOW()),
                                                                                 (2, 'INV-E5F6G7H8', 749.97, NOW()),
                                                                                 (3, 'INV-I9J0K1L2', 89.99,  NOW());

-- ── Step 6: Orders ────────────────────────────────────────────────────────────
INSERT INTO orders (
    order_id, order_ref,
    del_street, del_city, del_state, del_zip_code, del_country,
    warehouse_id, user_id, payment_id, invoice_id, created_at
) VALUES
      (
          1, 'ORD-A1B2C3D4',
          '123 Maple Ave', 'New York', 'NY', '10001', 'US',
          1, 1, 1, 1, NOW()
      ),
      (
          2, 'ORD-E5F6G7H8',
          '456 Oak Street', 'San Francisco', 'CA', '94102', 'US',
          2, 2, 2, 2, NOW()
      ),
      (
          3, 'ORD-I9J0K1L2',
          '789 Pine Road', 'Chicago', 'IL', '60601', 'US',
          1, 3, 3, 3, NOW()
      );

-- ── Reset sequences so next auto-generated IDs don't collide ─────────────────
SELECT setval('cart_cart_id_seq',    (SELECT MAX(cart_id)    FROM cart));
SELECT setval('app_user_user_id_seq',(SELECT MAX(user_id)    FROM app_user));
SELECT setval('payment_payment_id_seq',(SELECT MAX(payment_id) FROM payment));
SELECT setval('invoice_invoice_id_seq',(SELECT MAX(invoice_id) FROM invoice));
SELECT setval('orders_order_id_seq', (SELECT MAX(order_id)   FROM orders));

---
Verification Queries

  -- Check all users with their carts
SELECT u.user_id, u.name, c.cart_id
FROM app_user u
         LEFT JOIN cart c ON u.cart_id = c.cart_id;

-- Check cart contents with product names
SELECT u.name AS user, p.product_name, ci.quantity
FROM cart_items ci
    JOIN cart c    ON ci.cart_id   = c.cart_id
    JOIN app_user u ON u.cart_id  = c.cart_id
    JOIN product p  ON ci.product_id = p.product_id;

-- Check all orders with user, warehouse, payment, invoice
SELECT
    o.order_ref,
    u.name          AS user,
      w.warehouse_name AS warehouse,
      pay.payment_type,
      pay.amount,
      pay.status,
      inv.invoice_number,
      o.del_city,
      o.del_country,
      o.created_at
FROM orders o
    JOIN app_user u  ON o.user_id      = u.user_id
    JOIN warehouse w ON o.warehouse_id = w.warehouse_id
    JOIN payment pay ON o.payment_id   = pay.payment_id
    JOIN invoice inv ON o.invoice_id   = inv.invoice_id;