create table users(
    id integer not null auto_increment,
    username varchar(200) not null ,
    password varchar(200) not null ,
    created_at timestamp ,
    last_updated_at timestamp ,
    primary key (id)
) engine InnoDB;

select * from users;

alter table currencies
add last_updated_at timestamp;

create table currencies(
    id integer not null auto_increment,
    currency varchar(100) not null ,
    rate varchar(100) not null ,
    remark varchar(100) not null ,
    primary key (id)
) engine InnoDB;

select *
from currencies;

create table units(
    id integer not null auto_increment,
    name varchar(200) not null ,
    created_at timestamp ,
    last_updated_at timestamp ,
    primary key (id)
) engine InnoDB;

select *
from units;

desc units;

alter table units
    ADD CONSTRAINT uc_name UNIQUE (name);

create table items(
    id varchar(255) not null ,
    no_part varchar(200) not null ,
    description longtext ,
    hs_code integer ,
    item_type varchar(100) not null,
    unit_id integer not null ,
    created_at timestamp ,
    last_updated_at timestamp ,
    primary key (id),
    foreign key fk_units_items (unit_id) references units(id)
) engine InnoDB;

select *
from items;

desc items;

ALTER TABLE items
    MODIFY hs_code BIGINT;

create table suppliers(
    id varchar(255) not null ,
    name varchar(200) not null ,
    address varchar(200) not null ,
    phone bigint,
    created_at timestamp ,
    last_updated_at timestamp ,
    primary key (id)
) engine InnoDB;

select *
from suppliers;

desc suppliers;

alter table suppliers
modify phone varchar(100);

delete
from items
where unit_id = '89';

create table customers(
    id varchar(255) not null ,
    name varchar(200) not null ,
    address varchar(200) ,
    phone varchar(100) ,
    created_at timestamp ,
    last_updated_at timestamp ,
    primary key (id)
) engine InnoDB;

select * from customers;

desc customers;

truncate table units;

select *
from units;

delete from items;

truncate table units;

truncate table users;

truncate table suppliers;

select * from users;

