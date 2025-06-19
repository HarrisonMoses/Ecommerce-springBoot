

create table "user"
(
    id       bigint       not null
        constraint user_pk
            primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null
);

alter table "user"
    owner to postgres;

create table adresses
(
    id      bigint       not null
        constraint adresses_pk
            primary key,
    street  varchar(255) not null,
    user_id bigint       not null
        constraint adresses_user_id_fk
            references "user",
    city    varchar(255) not null,
    zip     varchar
);

alter table adresses
    owner to postgres;

