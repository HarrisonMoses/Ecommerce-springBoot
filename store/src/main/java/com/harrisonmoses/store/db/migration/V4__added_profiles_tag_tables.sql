create table public.profile
(
    id             bigint not null
        primary key
        references public."user",
    bio            text,
    phone_number   varchar(15),
    date_of_birth  date,
    loyalty_points int default 0
);

create table public.tag
(
    id   BIGINT      not null
        primary key,
    name varchar(25) not null
);
create table public.user_tag
(
    user_id bigint not null
        references public."user"
            on delete cascade,
    tag_id     bigint not null
        references public.tag
            on delete cascade,
    primary key (tag_id, user_id)
);
