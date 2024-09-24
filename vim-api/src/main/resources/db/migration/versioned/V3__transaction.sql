create table transaction
(
  id      uuid
    constraint pk_transactions primary key,
  item_id uuid        not null,
  type    varchar(50) not null check (type in
                                      ('CHECK_IN', 'CHECK_OUT', 'MARK_DAMAGED', 'MARK_REPAIRED',
                                       'REMOVE')),
  created timestamp   not null,
  updated timestamp   not null
);