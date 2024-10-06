insert into user_tab (id, username)
values (1, 'dbuser1');
insert into password_tab (user_id, password)
values (1, '{noop}dbpwd1');
insert into authority_tab (user_id, authority)
values (1, 'ROLE_USER'),
       (1, 'ROLE_DB_USER');
