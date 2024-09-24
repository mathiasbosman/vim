create table refresh_token
(
  id         uuid      not null
    constraint refresh_token_pk
      primary key,
  user_id    uuid      not null,
  token      uuid      not null,
  expires_at timestamp not null
);

create unique index refresh_token_id_uindex
  on refresh_token (id);

create unique index refresh_token_user_id_uindex
  on refresh_token (user_id);

