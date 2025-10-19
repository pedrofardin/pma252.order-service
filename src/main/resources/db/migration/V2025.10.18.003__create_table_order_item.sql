CREATE TABLE order_item
(
    id varchar(36) NOT NULL,
    order_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    quantity int NOT NULL,
    total decimal(10, 2) NOT NULL,
    CONSTRAINT order_item_pkey PRIMARY KEY (id),
    CONSTRAINT order_item_order_fk FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE INDEX idx_order_item_order_id ON order_item (order_id);
