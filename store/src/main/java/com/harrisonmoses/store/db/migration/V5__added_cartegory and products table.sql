create table public.cartegory
(
    id int not null primary key ,
    name  varchar(255) not null
);

create table public.product
(
    id  bigint  not null   primary key,
    name   varchar(30) not null,
    price  decimal(10,2) not null ,
    description text  not null,
    cartegory_id   bigint  not null
        references public.cartegory
);