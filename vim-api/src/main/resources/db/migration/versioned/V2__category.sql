create table category
(
  id                 uuid
    constraint pk_item_category primary key,
  name               varchar(255) not null unique,
  code               varchar(50)  not null unique,
  parent_category_id uuid references category (id),
  deleted            boolean default false,

  created            timestamp    not null,
  updated            timestamp    not null,
  deleted_at         timestamp
);