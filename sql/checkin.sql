DROP DATABASE IF EXISTS checkin;
CREATE DATABASE checkin;
USE checkin;
DROP TABLE IF EXISTS student;
CREATE TABLE student(
    stu_num varchar(20) primary key,
    stu_pass varchar(20) not null,
    mail varchar(20),
    destination varchar(20),
    reason varchar(30),
    start_day varchar(2),
    start_time varchar(2),
    end_day varchar(2),
    end_time varchar(2),
    cron varchar(20),
    check_enable tinyint not null default 0
);


