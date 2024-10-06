create table user_tab (
    id int primary key,
    username varchar not null unique
);

create table password_tab (
    id serial primary key,
    user_id int not null unique references user_tab(id),
    password text
);

create table authority_tab (
    id serial primary key,
    user_id int not null references user_tab(id),
    authority varchar not null
);
