#CREATE SCHEMA `HRManagement` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name varchar(255) NOT NULL UNIQUE
) ENGINE = InnoDB;

CREATE TABLE employees (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  email varchar(50) NOT NULL UNIQUE,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  roles_id bigint NOT NULL,

  CONSTRAINT FOREIGN KEY (roles_id) REFERENCES roles(id)
) ENGINE = InnoDB;

CREATE TABLE posts (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name varchar(255) NOT NULL UNIQUE,
  salary_hour_rate DECIMAL NOT NULL
) ENGINE = InnoDB;

CREATE TABLE departments (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name varchar(255) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE employees_posts_departments (
  employees_id bigint NOT NULL PRIMARY KEY ,
  posts_id bigint NOT NULL,
  departments_id bigint NOT NULL,

  CONSTRAINT FOREIGN KEY (employees_id) REFERENCES employees(id),
  CONSTRAINT FOREIGN KEY (posts_id) REFERENCES posts(id),
  CONSTRAINT FOREIGN KEY (departments_id) REFERENCES departments(id)
) ENGINE = InnoDB;


CREATE TABLE events (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name varchar(255) NOT NULL UNIQUE,
  salary_coef DECIMAL NOT NULL
) ENGINE = InnoDB;

CREATE TABLE status (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name varchar(255) NOT NULL,
  salary_coef DECIMAL NOT NULL
) ENGINE = InnoDB;

-- Table with working hour f this month. Date in this table have format yyyy-mm-dd
-- hour = sum working hour per day
CREATE TABLE working_hour(
  employees_id BIGINT NOT NULL,
  date DATE NOT NULL ,
  status_id BIGINT NOT NULL ,
  events_id BIGINT NOT NULL ,
  hours DECIMAL NOT NULL ,

  CONSTRAINT FOREIGN KEY (employees_id) REFERENCES employees(id),
  CONSTRAINT FOREIGN KEY (status_id) REFERENCES status(id),
  CONSTRAINT FOREIGN KEY (events_id) REFERENCES events(id),
  CONSTRAINT UNIQUE (employees_id,date,status_id, events_id)
) ENGINE = InnoDB;

-- Table with archive working hour f this month. Date in this table have format yyyy-mm
-- hour = sum working hours per month
CREATE TABLE salary_archive(
  emploees_id BIGINT NOT NULL ,
  date DATE NOT NULL ,
  status_id BIGINT NOT NULL ,
  events_id BIGINT NOT NULL ,
  hours DECIMAL NOT NULL ,

  CONSTRAINT FOREIGN KEY (emploees_id) REFERENCES  employees(id),
  CONSTRAINT FOREIGN KEY (status_id) REFERENCES status(id),
  CONSTRAINT FOREIGN KEY (events_id) REFERENCES events(id),
  CONSTRAINT UNIQUE (emploees_id,date,status_id,events_id)
) ENGINE = InnoDB;
