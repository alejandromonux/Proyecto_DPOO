DROP DATABASE IF EXISTS centralized_restaurant;
CREATE DATABASE centralized_restaurant;
USE centralized_restaurant;

DROP TABLE IF EXISTS worker;
CREATE TABLE worker(
  username VARCHAR(255) NOT NULL PRIMARY KEY,
  email VARCHAR(255),
  password VARCHAR(255),
  connected boolean
);

DROP TABLE IF EXISTS configuration;
CREATE TABLE configuration (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  worker_name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS dish;
CREATE TABLE dish (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) UNIQUE,
  units INT DEFAULT 0,
  historics_orders INT DEFAULT 0,
  cost DOUBLE,
  timecost INT,
  active boolean
);

DROP TABLE IF EXISTS mesa;
CREATE TABLE mesa(
  name VARCHAR(255) NOT NULL PRIMARY KEY,
  chairs INT DEFAULT 1,
  in_use boolean DEFAULT false,
  active boolean default true
);

DROP TABLE IF EXISTS request;
CREATE TABLE request(
  id INT AUTO_INCREMENT PRIMARY KEY,
  mesa_name VARCHAR(255),
  name VARCHAR(255),
  quantity INT NOT NULL,
  in_service INT,
  password VARCHAR(255)
);


DROP TABLE IF EXISTS request_order;
CREATE TABLE request_order(
  request_id INT NOT NULL,
  dish_id INT NOT NULL,
  quantity INT,
  actual_service INT,
  activation_date DATE,
  PRIMARY KEY (request_id, dish_id),
  FOREIGN KEY (request_id) REFERENCES request(id),
  FOREIGN KEY (dish_id) REFERENCES dish(id)

);

DROP TABLE IF EXISTS configuration_dish;
CREATE TABLE configuration_dish(
  configuration_id INT NOT NULL,
  dish_id INT NOT NULL,
  PRIMARY KEY (configuration_id, dish_id)
);

ALTER TABLE configuration_dish ADD FOREIGN KEY (configuration_id) REFERENCES configuration(id);
ALTER TABLE configuration_dish ADD FOREIGN KEY (dish_id) REFERENCES dish(id);

DROP TABLE IF EXISTS configuration_table;
CREATE TABLE configuration_table(
  configuration_id INT NOT NULL,
  mesa_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (configuration_id, mesa_name)
);

ALTER TABLE configuration_table ADD FOREIGN KEY (configuration_id) REFERENCES configuration(id);
ALTER TABLE configuration_table ADD FOREIGN KEY (mesa_name) REFERENCES mesa(name);

DROP TABLE IF EXISTS variables_importantes;
CREATE TABLE variables_importantes(
  estado_servicio INT
);

INSERT INTO variables_importantes VALUES(0);
INSERT INTO request(name, quantity, in_service) VALUES('asdasd', 2, 0);