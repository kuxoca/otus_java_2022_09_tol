BEGIN;

INSERT INTO address (id, street) VALUES (1, 'Ленина');
INSERT INTO address (id, street) VALUES (2, 'Варшавское шоссе');
INSERT INTO address (id, street) VALUES (3, 'Земская');
INSERT INTO address (id, street) VALUES (4, 'Турова');
INSERT INTO address (id, street) VALUES (5, 'Арбат');

INSERT INTO client (id, name, address_id) VALUES (6, 'Павел', 1);
INSERT INTO client (id, name, address_id) VALUES (7, 'Григорий', 3);
INSERT INTO client (id, name, address_id) VALUES (8, 'Кристина', 4);
INSERT INTO client (id, name, address_id) VALUES (9, 'Александра', 5);
INSERT INTO client (id, name, address_id) VALUES (10, 'Дарья', 2);

INSERT INTO phone (id, number, client_id) VALUES (11, '8-999-111-22-33', 6);
INSERT INTO phone (id, number, client_id) VALUES (12, '8-999-222-33-44', 7);
INSERT INTO phone (id, number, client_id) VALUES (13, '8-999-333-44-55', 8);
INSERT INTO phone (id, number, client_id) VALUES (14, '8-999-444-55-66', 9);
INSERT INTO phone (id, number, client_id) VALUES (15, '8-999-555-66-77', 10);
INSERT INTO phone (id, number, client_id) VALUES (16, '8-999-666-77-88', 9);
INSERT INTO phone (id, number, client_id) VALUES (17, '8-999-777-88-99', 7);

COMMIT;

-- ALTER SEQUENCE hibernate_sequence RESTART WITH 18;
-- UPDATE t SET idcolumn=nextval('hibernate_sequence');

-- ALTER TABLE client ADD address_id BIGINT;
--
-- ALTER TABLE client ADD CONSTRAINT fk_address_id FOREIGN KEY (address_id) references address(id);
--
-- ALTER TABLE phone ADD client_id BIGINT;
--
-- ALTER TABLE phone ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) references client(id);
