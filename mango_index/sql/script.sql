create database searchdb
CHARACTER SET 'utf8'
COLLATE 'utf8_general_ci';
use searchdb;
create table news (
	 id   int AUTO_INCREMENT,
	 title varchar(512),
	 content varchar(65535),
	 abstractcontent varchar(512),
	 url varchar(512),
	 imageurl varchar(128),
	 updatedtime varchar(64),
	 primary key(id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;