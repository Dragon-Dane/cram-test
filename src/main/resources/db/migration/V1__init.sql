-- auto-generated definition
create table daily_summary
(
    date                            datetime not null
        primary key,
    confirmed_cases_in_last_24_hour int      null,
    confirmed_new_cases_per_day     int      null,
    cumulative_confirmed_cases      int      null,
    cumulative_death                int      null,
    cumulative_recovery             int      null,
    daily_tests_for_covid19_        int      null,
    death_in_last_24_hour           int      null,
    recovery_in_last_24_hour        int      null,
    total_tests_for_covid19_        int      null
);

-- auto-generated definition
create table district_summary
(
    district_id   bigint       not null
        primary key,
    cases         int          null,
    total_death   int          null,
    lat_lon       varchar(255) null,
    total_recover int          null
);


create table district
(
    id         bigint auto_increment
        primary key,
    bbs_code   int          not null,
    name_bn    varchar(255) null,
    name_en    varchar(255) null,
    created_at datetime     null,
    updated_at datetime     null,
    lat        varchar(255) null,
    lon        varchar(255) null
);

