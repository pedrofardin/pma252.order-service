CREATE TABLE orders
(
    id varchar(36) NOT NULL,
    account_id varchar(36) NOT NULL,
    date timestamp NOT NULL,
    total decimal(10, 2) NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_orders_account_id ON orders (account_id);
