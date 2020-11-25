create table if not exists rally(
    id bigint not null primary key,
    "name" varchar(100) not null,
    start_date date   not null,
    end_date date   not null,
    country varchar(50) not null,
    modified_by varchar(50) not null,
    modified_date date  not null,
    created_by varchar(50) not null,
    created_date date  not null
);

create table if not exists rally_stage(
    id bigint not null primary key,
    track_name varchar(100) not null,
    stage_number smallint not null,
    stage_length decimal not null,
    rally_id bigint not null references rally(id),
    modified_by varchar(50) not null,
    modified_date date  not null,
    created_by varchar(50) not null,
    created_date date  not null
);

create table if not exists stage_result (
    id bigint not null primary key,
    competitor_pair_id bigint not null references competitor_pair(id),
    stage_id bigint not null references rally_stage(id),
    result_time varchar(8),
    interrupted boolean,
    modified_by varchar(50) not null,
    modified_date date  not null,
    created_by varchar(50) not null,
    created_date date  not null
);

