create table if not exists level
(
    id   varchar(26) not null
    constraint level_pk
    primary key,
    name varchar(255)
    );


create table if not exists place
(
    id       varchar(26) not null
    constraint place_pk
    primary key,
    name     varchar(255),
    country  varchar(255),
    level_id varchar(26)
    constraint place_level_id_fk
    references level
    );


create table if not exists cause
(
    id          varchar(26) not null
    constraint cause_pk
    primary key,
    name        varchar(255),
    description varchar(255)
    );


create table if not exists fixer
(
    id   varchar(26) not null
    constraint fixer_pk
    primary key,
    name varchar(255)
    );


create table if not exists downtime
(
    id          varchar(26) not null
    constraint downtime_pk
    primary key,
    begin_date  timestamp,
    end_date    timestamp,
    area        varchar(26)
    constraint dwt_area_id_fk
    references place,
    cause       varchar(26)
    constraint downtime_cause_id_fk
    references cause,
    description text,
    shift       varchar(10),
    brigade     varchar(10),
    fixer       varchar(26)
    constraint downtime_fixer_id_fk
    references fixer,
    responsible varchar(26),
    created     timestamp,
    created_by  varchar(26),
    updated     timestamp,
    update_by   varchar(26),
    is_active   boolean
    );

create index if not exists downtime_area_index
    on downtime (area);

create index if not exists downtime_begin_date_index
    on downtime (begin_date);

create index if not exists downtime_cause_index
    on downtime (cause);

