--============DROP-TABLES============--
DROP TABLE invoice       CASCADE CONSTRAINTS PURGE;
DROP TABLE invoice_line  CASCADE CONSTRAINTS PURGE;
DROP TABLE locations     CASCADE CONSTRAINTS PURGE;
DROP TABLE poi           CASCADE CONSTRAINTS PURGE;
DROP TABLE "users"       CASCADE CONSTRAINTS PURGE;
DROP TABLE "admins"      CASCADE CONSTRAINTS PURGE;
DROP TABLE "clients"     CASCADE CONSTRAINTS PURGE;
DROP TABLE parks         CASCADE CONSTRAINTS PURGE;
DROP TABLE trip          CASCADE CONSTRAINTS PURGE;
DROP TABLE finished_trip CASCADE CONSTRAINTS PURGE;
DROP TABLE paths         CASCADE CONSTRAINTS PURGE;
DROP TABLE vehicles      CASCADE CONSTRAINTS PURGE;
DROP TABLE bicycles      CASCADE CONSTRAINTS PURGE;
DROP TABLE scooters      CASCADE CONSTRAINTS PURGE;
--============END-DROP-TABLES============--

--============CREATE-TABLES============--
CREATE TABLE locations (
    id_location INTEGER GENERATED AS IDENTITY CONSTRAINT pk_locations_id_location PRIMARY KEY,
    latitude    FLOAT                         CONSTRAINT nn_locations_latitude    NOT NULL,
    longitude   FLOAT                         CONSTRAINT nn_locations_longitude   NOT NULL,
    elevation   INTEGER
);

CREATE TABLE poi (
    id_poi        INTEGER GENERATED AS IDENTITY CONSTRAINT pk_poi_id_poi      PRIMARY KEY,
    id_location   INTEGER                       CONSTRAINT nn_poi_id_location NOT NULL,
    "description" VARCHAR(100)                  CONSTRAINT nn_poi_description NOT NULL
);
ALTER TABLE poi ADD CONSTRAINT fk_poi_id_location FOREIGN KEY (id_location) REFERENCES locations(id_location);

CREATE TABLE "users" (
    id_user VARCHAR(50)  CONSTRAINT pk_user_id_user PRIMARY KEY,
    pass    VARCHAR(200) CONSTRAINT nn_users_pass   NOT NULL
);

CREATE TABLE "admins"( 
    id_user VARCHAR(50) CONSTRAINT pk_admin_id_user PRIMARY KEY
);
ALTER TABLE "admins" ADD CONSTRAINT fk_admin_id_user FOREIGN KEY (id_user) REFERENCES "users"(id_user);

CREATE TABLE "clients"( 
    id_user               VARCHAR(50)  CONSTRAINT pk_client_id_user      PRIMARY KEY,
    email                 VARCHAR(100) CONSTRAINT nn_clients_email       NOT NULL,
    credit_card           VARCHAR(16)  CONSTRAINT nn_clients_credit_card NOT NULL,
    height                INTEGER      CONSTRAINT nn_clients_height      NOT NULL,
    weight                INTEGER      CONSTRAINT nn_clients_weight      NOT NULL,
    gender                VARCHAR(50)  CONSTRAINT nn_clients_gender      NOT NULL,
    cycling_average_speed NUMBER(4,2)  CONSTRAINT nn_clients_cycl_avg    NOT NULL,
    actual_points          INTEGER      CONSTRAINT ck_negative_points     CHECK(actual_points>=0)
);
ALTER TABLE "clients" ADD CONSTRAINT fk_client_id_user FOREIGN KEY (id_user) REFERENCES "users"(id_user);

CREATE TABLE vehicles(
    id_vehicle       VARCHAR(50) CONSTRAINT pk_vehicles_id_vehicle       PRIMARY KEY,
    id_location      INTEGER     CONSTRAINT nn_vehicles_id_location      NOT NULL,
    weight           INTEGER     CONSTRAINT nn_vehicles_weight           NOT NULL,
    frontal_area     NUMBER(2,1) CONSTRAINT nn_vehicles_frontal_area     NOT NULL,
    aerodynamic_coef NUMBER(3,2) CONSTRAINT nn_vehicles_aerodynamic_coef NOT NULL
);
ALTER TABLE vehicles ADD CONSTRAINT fk_vehicles_id_location FOREIGN KEY (id_location) REFERENCES locations(id_location); 

CREATE TABLE bicycles(
    id_vehicle VARCHAR(50)CONSTRAINT pk_bicycles_id_vehicle PRIMARY KEY,
    wheel_size INTEGER    CONSTRAINT nn_bicycles_wheel_size NOT NULL
);
ALTER TABLE bicycles ADD CONSTRAINT fk_bicycles_id_vehicle FOREIGN KEY (id_vehicle) REFERENCES vehicles(id_vehicle);

CREATE TABLE scooters(
    id_vehicle              VARCHAR(50)  CONSTRAINT pk_scooters_id_vehicle      PRIMARY KEY,
    max_battery_capacity    NUMBER(10)  CONSTRAINT nn_scooters_max_bat_cap     NOT NULL,  
    actual_battery_capacity INTEGER      CONSTRAINT nn_scooters_actual_batt_cap NOT NULL,
    "type"                  VARCHAR(100) CONSTRAINT nn_scooters_type            NOT NULL,
    "power"                 NUMBER(10)  CONSTRAINT nn_scooters_power           NOT NULL
);
ALTER TABLE scooters ADD CONSTRAINT fk_scooters_id_vehicle FOREIGN KEY (id_vehicle) REFERENCES vehicles(id_vehicle);

CREATE TABLE parks(
    id_park       VARCHAR(50)  CONSTRAINT pk_parks_id_park       PRIMARY KEY,
    id_location   INTEGER      CONSTRAINT nn_parks_id_location   NOT NULL,
    "description" VARCHAR(200) CONSTRAINT nn_parks_description   NOT NULL,
    max_bike      INTEGER      CONSTRAINT nn_parks_max_bike      NOT NULL,
    max_scooter   INTEGER      CONSTRAINT nn_parks_max_scooter   NOT NULL,
    input_voltage NUMBER(5,2)  CONSTRAINT nn_parks_input_voltage NOT NULL,
    input_current NUMBER(5,2)  CONSTRAINT nn_parks_input_current NOT NULL 
);
ALTER TABLE parks ADD CONSTRAINT fk_parks_id_location FOREIGN KEY (id_location) REFERENCES locations(id_location);

CREATE TABLE trip(
    id_trip      INTEGER GENERATED AS IDENTITY CONSTRAINT pk_trips_id_trip      PRIMARY KEY,
    id_vehicle   VARCHAR(50)                   CONSTRAINT nn_trips_id_vehicle   NOT NULL,
    start_park   VARCHAR(50)                   CONSTRAINT nn_trips_start_park   NOT NULL, 
    end_park     VARCHAR(50),
    id_user      VARCHAR(50)                   CONSTRAINT nn_trips_id_user      NOT NULL,
    initial_date DATE                          CONSTRAINT nn_trips_initial_date NOT NULL
);
ALTER TABLE trip ADD CONSTRAINT fk_trips_id_vehicle  FOREIGN KEY (id_vehicle)  REFERENCES vehicles(id_vehicle);
ALTER TABLE trip ADD CONSTRAINT fk_trips_start_park  FOREIGN KEY (start_park)  REFERENCES parks(id_park);
ALTER TABLE trip ADD CONSTRAINT fk_trips_end_park    FOREIGN KEY (end_park)    REFERENCES parks(id_park);
ALTER TABLE trip ADD CONSTRAINT fk_trips_id_user     FOREIGN KEY (id_user)     REFERENCES "clients"(id_user);

CREATE TABLE finished_trip(
    id_finished_trip  INTEGER      CONSTRAINT pk_id_finished_trip       PRIMARY KEY,
    actual_end_park   VARCHAR(50)  CONSTRAINT nn_trips_actual_end_park  NOT NULL,
    end_date DATE
);
ALTER TABLE finished_trip ADD CONSTRAINT fk_id_finished_trip        FOREIGN KEY (id_finished_trip) REFERENCES trip(id_trip);
ALTER TABLE finished_trip ADD CONSTRAINT fk_finished_trip_end_park  FOREIGN KEY (actual_end_park)  REFERENCES parks(id_park);

CREATE TABLE paths(
    "start"             INTEGER,
    "end"               INTEGER,
    kinetic_coefficient NUMBER(5,3) DEFAULT ON NULL 0,
    wind_direction      NUMBER(4,1) DEFAULT ON NULL 0,
    wind_speed          NUMBER(4,1) DEFAULT ON NULL 0,
    
    CONSTRAINT pk_paths PRIMARY KEY ("start","end")
);
ALTER TABLE paths ADD CONSTRAINT fk_paths_start FOREIGN KEY ("start") REFERENCES locations(id_location);
ALTER TABLE paths ADD CONSTRAINT fk_paths_end   FOREIGN KEY ("end")   REFERENCES locations(id_location);

CREATE TABLE invoice(
    id_invoice    INTEGER GENERATED AS IDENTITY CONSTRAINT pk_id_invoice PRIMARY KEY,
    id_user       VARCHAR(50),
    emission_date DATE,
    "month"       INTEGER,
    "year"        INTEGER
);
ALTER TABLE invoice ADD CONSTRAINT fk_id_user_invoice FOREIGN KEY (id_user) REFERENCES "clients"(id_user);

CREATE TABLE invoice_line(
    id_line     INTEGER GENERATED AS IDENTITY CONSTRAINT pk_id_line PRIMARY KEY,
    id_invoice  INTEGER,
    "cost"      NUMBER(*,2),
    trip_points INTEGER,
    id_finished_trip INTEGER
);
ALTER TABLE invoice_line ADD CONSTRAINT fk_id_invoice FOREIGN KEY (id_invoice) REFERENCES invoice(id_invoice);
ALTER TABLE invoice_line ADD CONSTRAINT fk_invoice_id_finished_trip FOREIGN KEY (id_finished_trip) REFERENCES finished_trip(id_finished_trip);
--============END-CREATE-TABLES============--



