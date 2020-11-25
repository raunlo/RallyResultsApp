create table if not exists api_key_owner (
    id bigint not null primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(70) not null,
    tel_number varchar(20),
    modified_by varchar(50) not null,
    modified_date DATE  not null,
    created_by varchar(50) not null,
    created_date DATE  not null
);

create table if not exists api_key (
    id bigint not null primary key,
    value varchar(100) not null,
    api_key_owner_id bigint not null references api_key_owner(id),
    valid boolean not null,
    modified_by varchar(50) not null,
    modified_date DATE  not null,
    created_by varchar(50) not null,
    created_date DATE  not null
);