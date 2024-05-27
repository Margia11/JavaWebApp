ALTER TABLE users
ADD COLUMN username varchar(250);

alter table users
add constraint username_contstraint unique (username);