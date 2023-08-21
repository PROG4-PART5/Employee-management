create table if not exists "position"
(
    id          varchar
        constraint cnaps_position_pk primary key default uuid_generate_v4(),
    name        varchar not null unique
);