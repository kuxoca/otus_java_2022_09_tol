drop table if exists clients;
create table clients
(
    id   bigserial not null primary key,
    name varchar(50)
);

drop table if exists addresses;
create table addresses
(
    id      bigserial not null primary key,
    street  varchar(100),
    client_id bigserial references clients (id)
);

drop table if exists phones;
create table phones
(
    id      bigserial not null primary key,
    number  varchar(20),
    client_id bigserial references clients (id)
);
