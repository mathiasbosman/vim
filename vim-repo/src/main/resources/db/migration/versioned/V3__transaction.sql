create table transaction
(
    id      uuid
        constraint pk_transactions primary key,
    item_id uuid         not null,
    type    varchar(50)  not null check (type in ('IN', 'OUT')),
    created timestamp    not null,
    updated timestamp    not null
);