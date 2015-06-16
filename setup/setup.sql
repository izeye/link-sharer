CREATE DATABASE db_link_sharer CHARACTER SET utf8;

GRANT ALL PRIVILEGES ON db_link_sharer.* TO 'link_sharer'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON db_link_sharer.* TO 'link_sharer'@'localhost' IDENTIFIED BY '1234';

FLUSH PRIVILEGES;
