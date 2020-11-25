create table if not exists competitor (
    id bigint not null primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    modified_by varchar(50) not null,
    modified_date date  not null,
    created_by varchar(50) not null,
    created_date date  not null
);

create table if not exists competition_class (
    id bigint not null primary key,
    "value" varchar(50) not null
);

create table if not exists competitor_pair (
    id bigint not null primary key,
    competition_class_id bigint not null references competition_class(id),
    driver_id bigint not null references competitor(id),
    co_driver_id bigint not null references competitor(id),
    modified_by varchar(50) not null,
    modified_date date  not null,
    created_by varchar(50) not null,
    created_date date  not null
);

