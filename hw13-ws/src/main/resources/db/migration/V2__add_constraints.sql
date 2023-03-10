ALTER TABLE client ADD address_id BIGINT;

ALTER TABLE client ADD CONSTRAINT fk_address_id FOREIGN KEY (address_id) references address(id);

ALTER TABLE phone ADD client_id BIGINT;

ALTER TABLE phone ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) references client(id);

-- ALTER TABLE client ADD address_id BIGINT NOT NULL DEFAULT 0;
-- ALTER TABLE phone ADD client_id BIGINT NOT NULL DEFAULT 0;