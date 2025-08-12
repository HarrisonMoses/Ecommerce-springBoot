create table public.orders
(
    id bigint  not null constraint orders_pk  primary key,
    customer_id bigint not null  constraint orders_app_user_id_fk references public.app_user,
    status  varchar(20)  not null,
    created_at  date not null,
    total_price decimal(10, 2) not null
);

create table public.order_items
(
    id bigint  not null  constraint order_items_pk primary key,
    order_id    bigint            not null,
    product_id  bigint            not null,
    unit_price  decimal(10, 2)    not null,
    quantity    integer default 1 not null,
    total_price decimal(10, 2),
    constraint order_items_order_fk foreign key (order_id) references public."orders" (id),
    constraint order_items_product_fk foreign key (product_id) references public."product" (id)

);

