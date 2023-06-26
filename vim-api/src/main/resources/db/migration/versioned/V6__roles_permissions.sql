create table "role"
(
    id          uuid
        constraint pk_role primary key,
    name        text      not null,
    description text      not null,
    system_role boolean default false,
    created     timestamp not null,
    updated     timestamp not null
);
create unique index role_name_uindex
    on "role" (name);

/* Default system roles */
insert into "role" (id, name, description, system_role, created, updated)
values (gen_random_uuid(), 'admin', 'Administrator', true, now(), now());

insert into "role" (id, name, description, system_role, created, updated)
values (gen_random_uuid(), 'user', 'Default user role', true, now(), now());


create table "permission"
(
    id          uuid
        constraint pk_permission primary key,
    name        text not null,
    description text not null
);
create unique index permission_name_uindex on "permission" (name);

/* link tables */
create table "user_role"
(
    user_id uuid not null
        constraint fk_user_role_user_id
            references "user",
    role_id uuid not null
        constraint fk_user_role_role_id
            references "role",
    constraint pk_user_role
        primary key (user_id, role_id)
);

create table "role_permission"
(
    role_id       uuid not null
        constraint fk_role_permission_role_id references "role",
    permission_id uuid not null
        constraint fk_role_permission_permission_id references "permission",
    constraint pk_role_permission primary key (role_id, permission_id)
);
