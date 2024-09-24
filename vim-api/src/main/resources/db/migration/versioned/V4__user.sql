create table "user"
(
  id         uuid
    constraint pk_users primary key,
  username   text      not null,
  password   text      not null,

  deleted    boolean default false,
  created    timestamp not null,
  updated    timestamp not null,
  deleted_at timestamp
);

create unique index user_username_uindex
  on "user" (username);