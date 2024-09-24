create table item
(
  id          uuid
    constraint pk_items primary key,
  name        varchar(255) not null,
  brand       varchar(255),
  category_id uuid,
  status      varchar(255) not null check (status in
                                           ('AVAILABLE', 'UNAVAILABLE', 'RESERVED', 'CHECKED_OUT',
                                            'DAMAGED')),

  deleted     boolean default false,
  created     timestamp    not null,
  updated     timestamp    not null,
  deleted_at  timestamp
);