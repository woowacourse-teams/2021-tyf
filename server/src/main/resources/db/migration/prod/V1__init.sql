create table account
(
    id bigint not null auto_increment,
    created_at     timestamp not null,
    account_holder varchar(255),
    account_number varchar(255),
    bank           varchar(255),
    bankbook_url   varchar(255),
    status         varchar(255),
    primary key (id)
);

create table donation
(
    id bigint not null auto_increment,
    created_at timestamp not null,
    message    varchar(255),
    name       varchar(255),
    secret     boolean   not null,
    point      bigint    not null,
    status     varchar(255),
    creator_id bigint,
    donator_id bigint,
    primary key (id)
);

create table exchange
(
    id bigint not null auto_increment,
    created_at      timestamp not null,
    exchange_amount bigint,
    exchange_on     binary(255),
    status          varchar(255),
    member_id       bigint,
    primary key (id)
);

create table member
(
    id bigint not null auto_increment,
    created_at    timestamp not null,
    bio           varchar(1500),
    email         varchar(255),
    nickname      varchar(255),
    oauth2type    varchar(255),
    page_name     varchar(255),
    point         bigint    not null,
    profile_image varchar(255),
    account_id    bigint,
    primary key (id)
);

create table payment
(
    id bigint not null auto_increment,
    created_at        timestamp    not null,
    amount            bigint       not null,
    email             varchar(255),
    imp_uid           varchar(255),
    item_name         varchar(255) not null,
    merchant_uid      binary(16)    not null,
    status            varchar(255),
    member_id         bigint,
    refund_failure_id bigint,
    primary key (id)
);

create table refund_failure
(
    id bigint not null auto_increment,
    created_at       timestamp not null,
    remain_try_count integer   not null,
    primary key (id)
);

alter table account
    add constraint UK_66gkcp94endmotfwb8r4ocxm9
        unique (account_number);

alter table member
    add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email);

alter table member
    add constraint UK_hh9kg6jti4n1eoiertn2k6qsc unique (nickname);

alter table member
    add constraint UK_c1qrrh31sssfd2ef0kxr6uoy6 unique (page_name);

alter table donation
    add constraint FK1p1lwqxs91f1xrbv9b8x9uu60
        foreign key (creator_id)
            references member (id);

alter table donation
    add constraint FKkyw46epq4p3xtgqyweniyxwss
        foreign key (donator_id)
            references member (id);

alter table exchange
    add constraint FKgoowgr1muta8a7lo3diejm2d5
        foreign key (member_id)
            references member (id);

alter table member
    add constraint FK4jsivcqa7rxm6w59nggnpywh9
        foreign key (account_id)
            references account (id);

alter table payment
    add constraint FK4pswry4r5sx6j57cdeulh1hx8
        foreign key (member_id)
            references member (id);

alter table payment
    add constraint FK59ot7o1xka5aeebjfnbj46yua
        foreign key (refund_failure_id)
            references refund_failure (id);