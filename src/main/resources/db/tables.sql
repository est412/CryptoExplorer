CREATE TABLE algorithm (
  id identity NOT NULL,
  name varchar NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE alias (
  service_id int NOT NULL,
  algorithm_id int NOT NULL,
  alias_of_id int,
  PRIMARY KEY (service_id, algorithm_id)
);
CREATE INDEX ON alias (service_id);
CREATE INDEX ON alias (algorithm_id);

CREATE TABLE engine (
  id identity NOT NULL,
  name varchar NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE provider (
  id identity NOT NULL,
  name varchar NOT NULL,
  version double NOT NULL,
  class_name varchar NOT NULL,
  info varchar,
  PRIMARY KEY (id)
);

CREATE TABLE service (
  id identity NOT NULL,
  provider_id int NOT NULL,
  engine_id int NOT NULL,
  class_id int NOT NULL,
  PRIMARY KEY (id)
);
CREATE INDEX ON service (provider_id);
CREATE INDEX ON service (engine_id);
CREATE INDEX ON service (class_id);

CREATE TABLE service_class (
  id identity NOT NULL,
  name varchar NOT NULL,
  PRIMARY KEY (id)
);




