CREATE TABLE IF NOT EXISTS "have_position"
(

    id          varchar
        constraint have_cnaps_position_pk primary key default uuid_generate_v4(),
    employee_id varchar not null,
    position_id varchar not null,
    constraint have_cnaps_position_employee_fk foreign key (employee_id)
        references "cnaps_employee" (id),
    constraint have_position_position_fk foreign key (position_id)
        references "position" (id)
);