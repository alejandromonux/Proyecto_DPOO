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
  units_backup INT DEFAULT 0,
  historics_orders INT DEFAULT 0,
  cost FLOAT,
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
  id INT NOT NULL AUTO_INCREMENT,
  request_id INT NOT NULL,
  dish_id INT NOT NULL,
  quantity INT,
  actual_service INT,
  activation_date DATETIME,
  timecost INT,
  PRIMARY KEY (id, request_id, dish_id)
);


/*ALTER TABLE request_order
    ADD CONSTRAINT FOREIGN KEY (request_id) REFERENCES request(id);

ALTER TABLE request_order
    ADD CONSTRAINT FOREIGN KEY (dish_id) REFERENCES dish(id);*/

SELECT ro.id AS id FROM request_order AS ro;

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

SELECT * from request_order;


INSERT INTO request (mesa_name, name, quantity, in_service, password)
VALUES ('m15', 'reserva', 4,1,'1234');

SELECT * FROM request;

SELECT * FROM request_order;
SELECT * FROM dish;

SELECT ro.* FROM  request AS r, request_order AS ro WHERE r.id = ro.request_id AND r.id = 1;

SELECT ro.id, ro.dish_id, r.id , d.name, d.cost, ro.quantity,
                    d.timecost, ro.activation_date, ro.actual_service
                    FROM request AS r, request_order AS ro, dish AS d WHERE r.id = ro.request_id AND r.id = 1 AND d.id = ro.dish_id;
