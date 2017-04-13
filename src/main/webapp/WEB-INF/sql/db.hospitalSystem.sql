DROP TABLE IF EXISTS patient;
CREATE TABLE patient(
	id int(8) NOT NULL AUTO_INCREMENT,
	username varchar(16) DEFAULT NULL,
	password varchar(16) DEFAULT NULL,
	truename varchar(16) DEFAULT NULL,
	gender varchar(8) DEFAULT NULL,
	age int(2) DEFAULT NULL,
	description varchar(128) DEFAULT NULL,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS admin;
CREATE TABLE admin(
	id int(8) NOT NULL AUTO_INCREMENT,
	username varchar(16) DEFAULT '',
	password varchar(16) DEFAULT NULL,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS section;
CREATE TABLE section(
	id int(8) NOT NULL AUTO_INCREMENT,
	name varchar(16) DEFAULT NULL,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS doctor;
CREATE TABLE doctor(
	id int(8) NOT NULL AUTO_INCREMENT,
	name varchar(16) DEFAULT NULL,
	gender varchar(8) DEFAULT NULL,
	age int(2) DEFAULT NULL,
	school varchar(32) DEFAULT NULL,
	worktime int(2) DEFAULT NULL,
	sectionId int(8) DEFAULT NULL,
	title varchar(32) DEFAULT NULL,
	skill varchar(32) DEFAULT NULL,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;
ALTER TABLE doctor ADD CONSTRAINT fk_doctor_sectionId FOREIGN KEY (sectionId)
REFERENCES section(id);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
	id int(8) NOT NULL AUTO_INCREMENT,
	patientId int(8) DEFAULT NULL,
	doctorId int(8) DEFAULT NULL,
	ordertime date DEFAULT NULL,
	waitnum int(8) DEFAULT NULL,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;
ALTER TABLE orders ADD CONSTRAINT fk_orders_patientId FOREIGN KEY (patientId)
REFERENCES patient(id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_doctorId FOREIGN KEY (doctorId)
REFERENCES doctor(id);

DROP TABLE IF EXISTS doctorWork;
CREATE TABLE doctorWork(
	id int(8) NOT NULL AUTO_INCREMENT,
	doctorId int(8) DEFAULT NULL,
	workdate date DEFAULT NULL,
	orderNum int(8) DEFAULT 0,
	maxNum int(8) DEFAULT 20,
	PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;
ALTER TABLE doctorWork ADD CONSTRAINT fk_doctorWork_doctorId FOREIGN KEY (doctorId)
REFERENCES doctor(id);