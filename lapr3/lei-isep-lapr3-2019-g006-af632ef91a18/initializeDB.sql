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
    max_battery_capacity    NUMBER(4,2)  CONSTRAINT nn_scooters_max_bat_cap     NOT NULL,  
    actual_battery_capacity INTEGER      CONSTRAINT nn_scooters_actual_batt_cap NOT NULL,
    "type"                  VARCHAR(100) CONSTRAINT nn_scooters_type            NOT NULL,
    "power"                 NUMBER(4,1)  CONSTRAINT nn_scooters_power           NOT NULL
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
    trip_points INTEGER
);
ALTER TABLE invoice_line ADD CONSTRAINT fk_id_invoice FOREIGN KEY (id_invoice) REFERENCES invoice(id_invoice);
--============END-CREATE-TABLES============--

--====================FUNCTION ADD LOCATION====================
CREATE OR REPLACE FUNCTION addLocation(p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER) RETURN INTEGER 
IS
    temp_location INTEGER;
    already_exists EXCEPTION;
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations
    WHERE latitude = p_latitude AND 
          longitude = p_longitude AND 
          elevation = p_elevation;
    
    RAISE already_exists;
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
    
    INSERT INTO locations(latitude,longitude,elevation) VALUES (p_latitude,p_longitude,p_elevation);        
    SELECT id_location INTO temp_location 
    FROM locations
    WHERE latitude =p_latitude AND 
          longitude=p_longitude AND 
          elevation = p_elevation;
    RETURN(temp_location);

    WHEN already_exists THEN
        raise_application_error(-20001, 'Location already exists in the system'); 
END;    
/
--=============================================================

--======FUNCTION TO GET ID COORDS WITHOUT ELEVATION============
CREATE OR REPLACE FUNCTION getIDFromCoordsWithoutElevation(p_latitude FLOAT, p_longitude FLOAT) RETURN VARCHAR
IS
    res_park_id VARCHAR(50);
    temp_location_id INTEGER;
BEGIN
    SELECT p.id_park INTO res_park_id
    FROM parks p
    INNER JOIN locations l
    ON p.id_location = l.id_location
    WHERE l.latitude = p_latitude AND 
          l.longitude = p_longitude;
    RETURN(res_park_id);

    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20027, 'No Park found'); 
END;
/
--=============================================================

--===========FUNCTION TO CHECK LOCATION========================
CREATE OR REPLACE FUNCTION checkLocation(p_latitude FLOAT, p_longitude FLOAT , p_elevation INTEGER) RETURN INTEGER
IS
    summ INTEGER;
BEGIN  
    SELECT COUNT(id_location) INTO summ
    FROM locations
    WHERE latitude = p_latitude AND 
          longitude = p_longitude AND 
          elevation = p_elevation;  
    RETURN(summ);
END;
/
--=============================================================

--====================PROCEDURE TO ADD POI=====================
CREATE OR REPLACE PROCEDURE addPOI(p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER, p_description VARCHAR)
IS
    id_location INTEGER;
BEGIN
    id_location := addLocation(p_latitude,p_longitude,p_elevation);
    INSERT INTO POI(id_location,"description") VALUES (id_location,p_description);
END;
/
--=============================================================

--================FUNCTION GET LOCATION ID=====================
CREATE OR REPLACE FUNCTION getLocationID(p_latitude NUMBER, p_longitude NUMBER, p_elevation INTEGER) RETURN INTEGER
IS
    temp_location INTEGER;
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations
    WHERE latitude = p_latitude AND 
          longitude = p_longitude AND 
          elevation = p_elevation;
    
    RETURN(temp_location);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20002, 'Location not found'); 
END;    
/
--=============================================================

--===================FUNCTION GET POI==========================
CREATE OR REPLACE FUNCTION getPOI(p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER) RETURN SYS_REFCURSOR
IS 
    poi_cursor SYS_REFCURSOR;
    temp_location_id INTEGER;
BEGIN
    temp_location_id := getLocationID(p_latitude,p_longitude, p_elevation);
    
    OPEN poi_cursor FOR
        SELECT p."description" , l.latitude, l.longitude, l.elevation
        FROM poi p , locations l 
        WHERE p.id_location = temp_location_id AND 
              p.id_location=l.id_location;
    RETURN(poi_cursor);
    
END;
/
--=============================================================

--====================PROCEDURE TO ADD PARK====================
CREATE OR REPLACE PROCEDURE addPark(p_id_park VARCHAR, p_latitude FLOAT, p_longitude FLOAT, p_elevation  INTEGER,
                                    p_description VARCHAR, p_max_bike INT, p_max_scooter INT,p_input_voltage NUMBER,
                                    p_input_current NUMBER)
IS
    temp_location VARCHAR(10);
    loc INTEGER;
    ex_park_already_exists EXCEPTION;
BEGIN
    loc := checkLocation(p_latitude,p_longitude,p_elevation);
    
    IF(loc=1) THEN
        RAISE ex_park_already_exists;
    ELSE
        temp_location := addLocation(p_latitude,p_longitude,p_elevation);
    END IF;
    
    INSERT INTO parks(id_park,id_location,"description",max_bike,max_scooter,input_voltage,input_current)
        VALUES(p_id_park,temp_location,p_description,p_max_bike,p_max_scooter,p_input_voltage,p_input_current);
    
    EXCEPTION WHEN ex_park_already_exists THEN
        raise_application_error(-20003, 'Park already exists');
END;
/
--=============================================================

--=================FUNCTION TO GET PARK========================
CREATE OR REPLACE FUNCTION getPark(p_id_park VARCHAR) RETURN SYS_REFCURSOR
IS
    cp SYS_REFCURSOR;
BEGIN
    OPEN cp FOR
        SELECT p.id_park, l.latitude,l.longitude,l.elevation,
               p."description",p.max_bike,p.max_scooter,p.input_voltage,p.input_current
        FROM parks p, locations l
        WHERE p.id_location = l.id_location and        
              p.id_park = p_id_park;
    RETURN(cp);
END;
/
--=============================================================

--=================PROCEDURE TO REMOVE PARK====================
CREATE OR REPLACE PROCEDURE removePark(p_id_park VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_park_not_found EXCEPTION;
BEGIN
    verification := getPark(p_id_park);

    IF(verification IS NULL) THEN
        RAISE ex_park_not_found;
    ELSE
        DELETE FROM parks WHERE id_park = p_id_park; 
    END IF;

    EXCEPTION 
    WHEN ex_park_not_found THEN
        raise_application_error(-20004, 'Park not found'); 
END;
/
--=============================================================



--==============FUNCTION TO GET ALL PARKS======================
CREATE OR REPLACE FUNCTION getAllParks RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        SELECT p.id_park,l.latitude,l.longitude,p."description",
               p.max_bike,p.max_scooter,p.input_voltage,p.input_current
        FROM parks p,locations l
        WHERE p.id_location = l.id_location;  
    RETURN(my_cursor);
END;
/
--=============================================================

--==============PROCEDURE TO UPDATE PARK=======================
CREATE OR REPLACE PROCEDURE updatePark(p_idPark VARCHAR,new_latitude FLOAT, new_longitude FLOAT, new_elevation INTEGER,
                                       new_description VARCHAR, new_maxBike INTEGER, new_maxScooter INTEGER, new_inputVoltage FLOAT, 
                                       new_inputCurrent FLOAT)
IS
    verificationPark SYS_REFCURSOR;
    ex_park_not_found EXCEPTION;
    ex_location_already_occupied EXCEPTION;
    verificationLocation INTEGER;
    temp_location INTEGER;
BEGIN
    verificationPark := getPark(p_idPark);
    
    IF(verificationPark IS NULL) THEN
        RAISE ex_park_not_found;
    ELSE
        verificationLocation := checkLocation(new_latitude,new_longitude,new_elevation);
            IF(verificationLocation=1) THEN
                temp_location := getLocationID(new_latitude,new_longitude,new_elevation);
            ELSE
                temp_location := addLocation(new_latitude,new_longitude,new_elevation);
            END IF;

        UPDATE parks SET
            "description" = new_description , id_location = temp_location, 
            max_bike=new_maxBike , max_scooter=new_maxScooter,
            input_voltage = new_inputVoltage , input_current = new_inputCurrent
        WHERE id_park = p_idPark;

    END IF;

    EXCEPTION 
    WHEN ex_park_not_found THEN
        raise_application_error(-20005, 'Park not found'); 
END;
/
--=============================================================

--==========FUNCTION TO CHECK FREE SCOOTER PLACES==============
CREATE OR REPLACE FUNCTION checkFreeScooterPlaces(p_idPark VARCHAR) RETURN INTEGER
IS
    nr_free_places INTEGER;
    temp_location INTEGER;
BEGIN
    SELECT id_location INTO temp_location 
    FROM parks 
    WHERE id_park = p_idPark;
    
    SELECT p.max_scooter-(SELECT COUNT(s.id_vehicle) 
                          FROM vehicles v, scooters s
                          WHERE v.id_location = temp_location AND 
                                v.id_vehicle = s.id_vehicle) INTO nr_free_places
    FROM parks p
    WHERE p.id_location = temp_location;
    
    RETURN(nr_free_places);
END;
/
--=============================================================

--=========FUNCTION TO CHECK FREE BICYCLES PLACES==============
CREATE OR REPLACE FUNCTION checkFreeBicyclesPlaces(p_idPark VARCHAR) RETURN INTEGER
IS
    nr_free_places INTEGER;
    temp_location INTEGER;
BEGIN
    SELECT id_location INTO temp_location 
    FROM parks 
    WHERE id_park = p_idPark;
    
    SELECT p.max_bike-(SELECT COUNT(b.id_vehicle) 
                       FROM vehicles v, bicycles b
                       WHERE v.id_location = temp_location AND 
                             v.id_vehicle = b.id_vehicle) INTO nr_free_places
    FROM parks p
    WHERE p.id_location = temp_location;
    
    return(nr_free_places);
END;
/
--=============================================================

--=============FUNCTION TO GET ALL LOCATIONS===================
CREATE OR REPLACE FUNCTION getAllLocations RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        SELECT l.latitude,l.longitude, l.elevation
        FROM locations l
        WHERE l.id_location IN (SELECT id_location FROM parks) OR 
              l.id_location IN (SELECT id_location FROM poi);
    RETURN(my_cursor);
END;
/
--=============================================================

--===============PROCEDURE TO ADD BICYCLE======================
CREATE OR REPLACE PROCEDURE addBicycle(idVehicle VARCHAR, weight INTEGER, param_latitude FLOAT, param_longitude FLOAT, 
                                       aerodynamicCoef FLOAT, frontalArea FLOAT, wheelSize FLOAT) 
IS
    temp_location VARCHAR(10);
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations l 
    WHERE l.latitude = param_latitude AND 
          l.longitude = param_longitude;
    
    INSERT INTO vehicles(id_vehicle, id_location, weight, frontal_area, aerodynamic_coef) 
        VALUES (idVehicle, temp_location, weight, frontalArea, aerodynamicCoef);
    
    INSERT INTO bicycles(id_vehicle, wheel_size)
        VALUES (idVehicle, wheelSize);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20006, 'Location doesnt exist in the system');
END;
/
--=============================================================

--===============PROCEDURE TO ADD SCOOTER======================
CREATE OR REPLACE PROCEDURE addScooter(idVehicle VARCHAR, weight INTEGER, param_latitude FLOAT, param_longitude FLOAT,aerodynamicCoef FLOAT, 
                                       frontalArea FLOAT, maxBatteryCapacity FLOAT, actualBatteryCapaity FLOAT, param_type String, p_power FLOAT) 
IS
    temp_location VARCHAR(10);
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations l 
    WHERE l.latitude = param_latitude AND 
          l.longitude = param_longitude;
    
    INSERT INTO vehicles(id_vehicle, id_location, weight, frontal_area, aerodynamic_coef) 
        VALUES (idVehicle, temp_location, weight, frontalArea, aerodynamicCoef);
    INSERT INTO scooters(id_vehicle, max_battery_capacity, actual_battery_capacity, "type","power") 
        VALUES (idVehicle, maxBatteryCapacity, actualBatteryCapaity, param_type,p_power);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20007, 'Location doesnt exist in the system');
END;
/
--=============================================================

--===============FUNCTION TO GET BICYCLE=======================
CREATE OR REPLACE FUNCTION getBicycle(idVehicle VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
        SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, 
               v.aerodynamic_coef, v.frontal_area, b.wheel_size  
        FROM vehicles v
        INNER JOIN locations l
        ON v.id_location = l.id_location
        INNER JOIN bicycles b
        ON v.id_vehicle = b.id_vehicle
        WHERE v.id_vehicle = idVehicle;
    
    RETURN(v_rc);
END;
/
--=============================================================

--===============FUNCTION TO GET SCOOTER=======================
CREATE OR REPLACE FUNCTION getScooter(idVehicle VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, 
           v.frontal_area, s.max_battery_capacity, s.actual_battery_capacity, s."type", s."power"
    FROM vehicles v
    INNER JOIN locations l
    ON v.id_location = l.id_location
    INNER JOIN scooters s
    ON v.id_vehicle = s.id_vehicle
    WHERE v.id_vehicle = idVehicle;
       
    RETURN(v_rc);
END;
/
--=============================================================

--============PROCEDURE TO REMOVE BICYCLE======================
CREATE OR REPLACE PROCEDURE removeBicycle(p_idVehicle VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_bycicle_not_found EXCEPTION;
BEGIN
    verification := getBicycle(p_idVehicle);
    
    IF(verification IS NULL) THEN
        RAISE ex_bycicle_not_found;
    ELSE
    
        DELETE FROM bicycles WHERE id_vehicle = p_idVehicle;
        DELETE FROM vehicles WHERE id_vehicle = p_idVehicle;   
        
    END IF;
    
    EXCEPTION 
    WHEN ex_bycicle_not_found THEN
        raise_application_error(-20008, 'Bicycle not found'); 
END;
/
--=============================================================

--============PROCEDURE TO REMOVE SCOOTER======================
CREATE OR REPLACE PROCEDURE removeScooter(p_idVehicle VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_scooter_not_found EXCEPTION;
BEGIN
    verification := getScooter(p_idVehicle);
    
    IF(verification IS NULL) THEN
        RAISE ex_scooter_not_found;
    ELSE
    
        DELETE FROM scooters WHERE id_vehicle = p_idVehicle;
        DELETE FROM vehicles WHERE id_vehicle = p_idVehicle;   
        
    END IF;
    
    EXCEPTION 
    WHEN ex_scooter_not_found THEN
        raise_application_error(-20006, 'Scooter not found'); 
END;
/
--=============================================================

--============PROCEDURE TO UPDATE BICYCLE======================
CREATE OR REPLACE PROCEDURE updateBicycle(p_idVehicle VARCHAR,new_weight INTEGER,new_latitude FLOAT, new_longitude FLOAT,
                                          new_aerodynamicCoef FLOAT, new_frontalArea FLOAT, new_wheelSize INTEGER)
IS
    verificationBike SYS_REFCURSOR;
    ex_bicycle_not_found EXCEPTION;
    ex_locationPark_not_found EXCEPTION;
    verificationLocation INTEGER;
    new_location VARCHAR(10); 
BEGIN
    verificationBike := getBicycle(p_idVehicle);
    
    IF(verificationBike IS NULL) THEN
        RAISE ex_bicycle_not_found;
    ELSE
        verificationLocation := checkLocation(new_latitude,new_longitude,0);
        
        IF(verificationLocation=1) THEN
            new_location := getLocationID(new_latitude,new_longitude,0);
        ELSE
            RAISE ex_locationPark_not_found;
        END IF;        
        
        UPDATE vehicles SET
            weight = new_weight , id_location = new_location, 
            aerodynamic_coef = new_aerodynamicCoef , frontal_area = new_frontalArea
        WHERE id_vehicle = p_idVehicle;
        
        UPDATE bicycles SET
            wheel_size = new_wheelSize
        WHERE id_vehicle = p_idVehicle;
        
    END IF;
    
    EXCEPTION 
    WHEN ex_bicycle_not_found THEN
        raise_application_error(-20007, 'Bicycle not found'); 
        
    WHEN ex_locationPark_not_found THEN
        raise_application_error(-20008, 'Park location not found'); 
END;
/
--=============================================================

--============PROCEDURE TO UPDATE SCOOTER======================
CREATE OR REPLACE PROCEDURE updateScooter(p_idVehicle VARCHAR,new_weight INTEGER,new_latitude FLOAT, new_longitude FLOAT,new_aerodynamicCoef FLOAT, 
                                          new_frontalArea FLOAT,new_max_battery_capacity FLOAT, new_actual_battery_capacity FLOAT, new_type VARCHAR, new_power FLOAT)
IS
    verificationScooter SYS_REFCURSOR;
    ex_scooter_not_found EXCEPTION;
    ex_locationPark_not_found EXCEPTION;
    verificationLocation INTEGER;
    new_location VARCHAR(10); 
BEGIN
    verificationScooter := getScooter(p_idVehicle);
    
    IF(verificationScooter IS NULL) THEN
        RAISE ex_scooter_not_found;
    ELSE
        verificationLocation := checkLocation(new_latitude,new_longitude,0);
        
        IF(verificationLocation=1) THEN
            new_location := getLocationID(new_latitude,new_longitude,0);
        ELSE
            RAISE ex_locationPark_not_found;
        END IF;        
        
        UPDATE vehicles SET
            weight = new_weight , id_location = new_location, 
            aerodynamic_coef = new_aerodynamicCoef , frontal_area = new_frontalArea
        WHERE id_vehicle = p_idVehicle;
        
        UPDATE scooters SET
            max_battery_capacity = new_max_battery_capacity,
            actual_battery_capacity = new_actual_battery_capacity, 
            "power" = new_power, "type" = new_type
        WHERE id_vehicle = p_idVehicle;
  
    END IF;
    
    EXCEPTION 
    WHEN ex_scooter_not_found THEN
        raise_application_error(-20009, 'Scooter not found'); 
        
    WHEN ex_locationPark_not_found THEN
        raise_application_error(-20010, 'Park location not found'); 
END;
/
--=============================================================

--=========FUNCTION TO GET ALL SCOOTER OF A PARK===============
CREATE OR REPLACE FUNCTION getAllScootersOfPark(p_latitude FLOAT, p_longitude FLOAT) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
    temp_park_location INTEGER;
    t_id_park VARCHAR(10);
BEGIN
    t_id_park:=getIDFromCoordsWithoutElevation(p_latitude,p_longitude);
    
    SELECT p.id_location INTO temp_park_location
    FROM  parks p
    WHERE p.id_park = t_id_park;

    OPEN v_rc FOR
        SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, 
               v.frontal_area, s.max_battery_capacity, s.actual_battery_capacity, s."type", s."power"
        FROM vehicles v
        INNER JOIN locations l
        ON v.id_location = l.id_location
        INNER JOIN scooters s
        ON v.id_vehicle = s.id_vehicle
        WHERE v.id_location =temp_park_location;    
    RETURN(v_rc);
END;
/
--=============================================================

--=========FUNCTION TO GET ALL SCOOTERS OF A PARK==============
CREATE OR REPLACE FUNCTION getScooterFromSpecs(vehicleSpecs VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, v.frontal_area, s.max_battery_capacity, s.actual_battery_capacity, s."type", s."power"
    FROM vehicles v
    INNER JOIN locations l
    ON v.id_location = l.id_location
    INNER JOIN scooters s
    ON v.id_vehicle = s.id_vehicle
    WHERE s."type" = vehicleSpecs
    ORDER BY v.weight, v.aerodynamic_coef, v.frontal_area
    FETCH FIRST ROW ONLY;
       
    RETURN(v_rc);
END;
/
--=============================================================

--=============FUNCTION TO GET ALL SCOOTERS====================
CREATE OR REPLACE FUNCTION getAllScooters RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, 
               v.frontal_area, s.max_battery_capacity, s.actual_battery_capacity, s."type", s."power"
        FROM vehicles v
        INNER JOIN locations l
        ON v.id_location = l.id_location
        INNER JOIN scooters s
        ON v.id_vehicle = s.id_vehicle;
    RETURN(my_cursor);
END;
/
--=============================================================

--==========FUNCTION TO GET BICYCLE FROM SPECS=================
CREATE OR REPLACE FUNCTION getBicycleFromSpecs(vehicleSpecs VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
        SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, 
               v.aerodynamic_coef, v.frontal_area, b.wheel_size  
        FROM vehicles v
        INNER JOIN locations l
        ON v.id_location = l.id_location
        INNER JOIN bicycles b
        ON v.id_vehicle = b.id_vehicle
        WHERE b.wheel_size = vehicleSpecs
        ORDER BY v.weight, v.aerodynamic_coef, v.frontal_area
        FETCH FIRST ROW ONLY;
    RETURN(v_rc);
END;
/
--=============================================================

--==============PROCEDURE TO ADD CLIENT========================
CREATE OR REPLACE PROCEDURE addClient(id_user VARCHAR, pass VARCHAR, email VARCHAR, credit_card VARCHAR, height INTEGER, weight INTEGER, 
                                      gender VARCHAR, cycling_average_speed FLOAT) 
IS

BEGIN
        INSERT INTO "users"(id_user, pass) 
            VALUES (id_user, pass);
        INSERT INTO "clients"(id_user, email, weight, credit_card, height,cycling_average_speed,gender,actual_points) 
            VALUES (id_user, email, weight, credit_card, height,cycling_average_speed,gender,0);
END;
/
--=============================================================

--==============FUNCTION TO GET CLIENT=========================
CREATE OR REPLACE FUNCTION getClient(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT c.id_user, u.pass, c.email, c.credit_card, c.height, c.weight, c.gender, c.cycling_average_speed, c.actual_points
    FROM "clients" c, "users" u
    WHERE u.id_user = c.id_user AND
          c.id_user = p_id_user; 
    RETURN(v_rc);
END;
/
--=============================================================

--=============FUNCTION TO CHECK IF TRAVEL=====================
create or replace function checkIfTravel(p_id_user varchar) return integer
is
    count_trip integer;
begin
    select count(t.id_trip) into count_trip
    from trip t, finished_trip tr
    where t.id_trip not in (select id_finished_trip from finished_trip)
    and t.id_user = p_id_user;
    --SE DER ZERO NAO TEM NENHUMA VIAGEM POR TERMINAR
    return(count_trip);
end;
/
--=============================================================
--SET SERVEROUT ON;
--CREATE OR REPLACE TRIGGER checkIfTravel
--BEFORE INSERT OR UPDATE ON trip
--FOR EACH ROW
--DECLARE
--    t_id_trip  trip.id_trip%type;
--    t_id_vehicle trip.id_vehicle%type;
--    t_start_park trip.start_park%type;
--    t_end_park trip.end_park%type;   
--    t_id_user trip.id_user%type;
--    t_initial_date trip.initial_date%type;
--    temp_count_trip INTEGER;
--    ex_trip_not_finished EXCEPTION;
--    
--    CURSOR trips_of_user(p_id_user trip.id_user%type) IS
--        SELECT * FROM trip WHERE id_user = p_id_user;
--BEGIN
--    OPEN trips_of_user(:new.id_user);
--        LOOP
--        --FOR EVERY TRIP OF THE USER VERIFY IF THE ID IS IN THE FINISHED TRIP TABLE
--            FETCH trips_of_user INTO t_id_trip, t_id_vehicle, t_start_park, t_end_park, t_id_user, t_initial_date;
--            EXIT WHEN trips_of_user%notfound;
--            
--            SELECT COUNT(t.id_trip) INTO temp_count_trip
--            FROM finished_trip ft, trip t
--            WHERE t.id_trip NOT IN (SELECT id_finished_trip FROM finished_trip);
--            --EXISTS TRIPS WITH FINISHED TRIP
--            IF(temp_count_trip>0) THEN
--                RAISE ex_trip_not_finished;
--            END IF;
--        END LOOP;
--        
--    EXCEPTION 
--    WHEN ex_trip_not_finished THEN
--        raise_application_error(-20011,'User travelling.Cant request another trip');
--
--END checkIfTravel;
--/

--OPCIONAL AO TRIGGER

--===========FUNCTION TO GET POINTS OF TRIP====================
CREATE OR REPLACE FUNCTION getPointsOfTrip(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
    trip_user_verify INTEGER;
    temp_id_finished_trip finished_trip.id_finished_trip%type;
BEGIN
    SELECT ft.id_finished_trip INTO temp_id_finished_trip
    FROM finished_trip ft, trip t 
    WHERE t.id_trip = ft.id_finished_trip AND 
          t.id_user = p_id_user
    ORDER BY ft.end_date DESC FETCH FIRST ROW ONLY;

    OPEN my_cursor FOR
        SELECT LA.elevation A,LB.elevation B
        FROM trip t, locations LA, locations LB, finished_trip ft
        WHERE LA.id_location = (SELECT l.id_location 
                                FROM locations l, parks p1 
                                WHERE l.id_location = p1.id_location AND 
                                p1.id_park = t.start_park) AND
              LB.id_location = (SELECT l.id_location
                                FROM locations l,parks p2
                                WHERE l.id_location = p2.id_location AND
                                p2.id_park = ft.actual_end_park) AND 
              t.id_trip = temp_id_finished_trip;
    RETURN(my_cursor);

    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20011, 'User with all trip points updated or with no trips');     
END;
/
--=============================================================

--===========PROCEDURE TO ADD POINTS===========================
CREATE OR REPLACE FUNCTION addPoints(p_id_user VARCHAR) RETURN INTEGER
IS
    elevations_cursor SYS_REFCURSOR;
    i_elevationA INTEGER;
    i_elevationB INTEGER;
    temp_points INTEGER;
    dif_elevation INTEGER;
    ex_not_qualified_for_points EXCEPTION;
BEGIN
    elevations_cursor := getPointsOfTrip(p_id_user);
        LOOP
            FETCH elevations_cursor INTO i_elevationA, i_elevationB;
            EXIT WHEN elevations_cursor%notfound;
        END LOOP;   
    
        dif_elevation := i_elevationB-i_elevationA;

        IF(dif_elevation BETWEEN 0 AND 25) THEN
            RAISE ex_not_qualified_for_points;
        ELSE
            IF(dif_elevation BETWEEN 25 and 50) THEN
                temp_points := 5;
            ELSE
                temp_points := 15;
            END IF;
        END IF;

        UPDATE "clients" SET
        actual_points = actual_points + temp_points
        WHERE id_user = p_id_user;

        RETURN(temp_points);

    EXCEPTION
    WHEN ex_not_qualified_for_points THEN
        raise_application_error(-21111, 'Not qualified to get points');    
END;
/
--=============================================================

--===========FUNCTION TO GET POINTS FROM USER==================
CREATE OR REPLACE FUNCTION getPointsFromUser(p_id_user VARCHAR) RETURN INTEGER
IS
    a_points INTEGER;
BEGIN

    SELECT c.actual_points INTO a_points
    FROM "clients" c
    WHERE c.id_user = p_id_user;
    
    RETURN(a_points);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
    raise_application_error(-20013, 'Id user not found');
END;
/
--=============================================================

--===========PROCEDURE TO SET POINTS===========================
CREATE OR REPLACE PROCEDURE setPoints(p_id_user VARCHAR,p_points INTEGER)
IS
    t_id_user VARCHAR(10);
BEGIN
    
   SELECT id_user INTO t_id_user 
   FROM "clients" 
   WHERE id_user = p_id_user; 
   
    UPDATE "clients" SET
    actual_points = p_points
    WHERE id_user = p_id_user;
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
    raise_application_error(-20014, 'Id user not found');  
END;
/
--=============================================================



--===========PROCEDURE TO ADD PATH=============================
CREATE OR REPLACE PROCEDURE addPath(a_latitude FLOAT , a_longitude FLOAT , a_elevation INTEGER ,b_latitude FLOAT , 
                                    b_longitude FLOAT , b_elevation INTEGER , kin_coeff FLOAT , w_dir FLOAT , w_speed FLOAT)
IS
    res_a INTEGER;
    res_b INTEGER;
    id_location_a INTEGER;
    id_location_b INTEGER;
    temp_location INTEGER;
    already_exists EXCEPTION;
BEGIN
    res_a := checkLocation(a_latitude,a_longitude,a_elevation);
    res_b := checkLocation(b_latitude,b_longitude,b_elevation);
    IF(res_a = 1) THEN
        id_location_a := getLocationID(a_latitude,a_longitude,a_elevation);
        
        SELECT l2.id_location INTO temp_location  
        FROM locations l2 
        WHERE l2.id_location = id_location_a AND 
              l2.id_location IN (SELECT UNIQUE  l.id_location 
                                 FROM locations l , parks p , POI o 
                                 WHERE l.id_location = p.id_location OR 
                                       l.id_location = o.id_location);
    ELSE     
        RAISE already_exists;
    END IF;
    
    IF(res_b = 1) THEN
        id_location_b := getLocationID(b_latitude,b_longitude,b_elevation);
        
        SELECT l2.id_location INTO temp_location  
        FROM locations l2 
        WHERE l2.id_location = id_location_b AND 
              l2.id_location IN (SELECT UNIQUE  l.id_location 
                                 FROM locations l , parks p , POI o 
                                 WHERE l.id_location = p.id_location OR 
                                       l.id_location = o.id_location);
    ELSE     
        RAISE already_exists;
    END IF;

    INSERT INTO Paths("start","end",kinetic_coefficient,wind_direction,wind_speed) 
        VALUES (id_location_a,id_location_b,kin_coeff,w_dir,w_speed);
     
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20015, 'Location not found'); 
    
    WHEN already_exists THEN
        raise_application_error(-20016, 'Location already exists in the system'); 
END;
/
--=============================================================

--===========FUNCTION TO GET ALL PATHS=========================
CREATE OR REPLACE FUNCTION getAllPaths RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR    
        SELECT LA.latitude,LA.longitude,LA.elevation,
               LB.latitude,LB.longitude,LB.elevation, p.kinetic_coefficient,p.wind_direction,p.wind_speed
        FROM locations LA, locations LB, paths p
        WHERE LA.id_location = p."start" AND 
              LB.id_location = p."end";        
    RETURN(my_cursor);
END;
/
--=============================================================

--===========FUNCTION TO GET USER TRIP REPORTS=================
CREATE OR REPLACE FUNCTION getUserTripReport(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR  
        SELECT t.id_trip, t.id_vehicle, t.id_user,LA.latitude,LA.longitude,LA.elevation,
                                             LB.latitude,LB.longitude,LB.elevation, t.initial_date UNLOCKING_DATE, ft.end_date LOCKING_DATE
        FROM trip t, locations LA, locations LB, finished_trip ft
        WHERE LA.id_location = (SELECT l.id_location 
                                FROM locations l, parks p1 
                                WHERE l.id_location = p1.id_location AND 
                                      p1.id_park = t.start_park) AND
              LB.id_location = (SELECT l.id_location
                                FROM locations l, parks p2
                                WHERE l.id_location = p2.id_location AND 
                                      p2.id_park = ft.actual_end_park) AND 
             t.id_user = p_id_user AND t.id_trip = ft.id_finished_trip;
    RETURN(my_cursor);
END;
/
--=============================================================

--===========PROCEDURE TO ADD TRIP============================= 
CREATE OR REPLACE PROCEDURE addTrip (t_id_vehicle VARCHAR, a_latitude FLOAT, a_longitude FLOAT, a_elevation INTEGER,
                                     b_latitude FLOAT, b_longitude FLOAT, b_elevation INTEGER, t_id_user VARCHAR,t_unlocking_date DATE)
IS
    temp_location_id_a INTEGER;
    temp_location_id_b INTEGER;
    temp_id_park_a VARCHAR(50);
    temp_id_park_b VARCHAR(50);
    check_travelling INTEGER;
    ex_user_travelling EXCEPTION;
BEGIN
    temp_location_id_a := getLocationID(a_latitude,a_longitude,a_elevation);
    SELECT id_park INTO temp_id_park_a
    FROM parks
    WHERE id_location = temp_location_id_a;
    
    temp_location_id_b := getLocationID(b_latitude,b_longitude,b_elevation);
    SELECT id_park INTO temp_id_park_b
    FROM parks
    WHERE id_location = temp_location_id_b;
    
    check_travelling := checkIfTravel(t_id_user);
    
    IF(check_travelling=0)THEN
        INSERT INTO trip(id_vehicle,start_park,end_park,id_user,initial_date)
        VALUES(t_id_vehicle,temp_id_park_a,temp_id_park_b,t_id_user,t_unlocking_date);
    ELSE
        RAISE ex_user_travelling;
    END IF;
    
    EXCEPTION 
    
    WHEN NO_DATA_FOUND THEN
    raise_application_error(-20017, 'Park or Vehicle not found');
    
    WHEN ex_user_travelling THEN
    raise_application_error(-20018, 'User travelling');
END;
/
--=============================================================

--===========FUNCTION GET TRIP ID==============================
CREATE OR REPLACE FUNCTION getTripId(t_id_vehicle VARCHAR,t_id_park VARCHAR,t_id_user VARCHAR,t_initial_date DATE) RETURN INTEGER
IS
    a_id_trip INTEGER;
BEGIN
        SELECT ft.id_finished_trip INTO a_id_trip
        FROM finished_trip ft, trip t
        WHERE t.id_trip = ft.id_finished_trip AND
              t.id_vehicle = t_id_vehicle AND
              t.start_park = t_id_park AND
              t.id_user = t_id_user AND
              t.initial_date = t_initial_date;
    return(a_id_trip);
END;
/
--=============================================================

--===========PROCEDURE TO UPDATE TRIP==========================
CREATE OR REPLACE PROCEDURE updateTrip(t_id_vehicle VARCHAR,t_id_park VARCHAR,t_id_user VARCHAR, t_unlocking_date DATE,
new_latitude FLOAT, new_longitude FLOAT, t_locking_date DATE)
IS
    get_trip_id INTEGER;
    ex_trip_not_found EXCEPTION;
    ex_location_not_found EXCEPTION;
    verificationLocation INTEGER;
    temp_park_location VARCHAR(50);
BEGIN
    --RETURN THE ID OF THE TRIP WITH THE PARAMETERS
    get_trip_id := getTripId(t_id_vehicle,t_id_park,t_id_user,t_unlocking_date);

    IF(get_trip_id IS NULL) THEN
        RAISE ex_trip_not_found;
    ELSE
        temp_park_location := getIDFromCoordsWithoutElevation(new_latitude,new_longitude);
        
        INSERT INTO finished_trip(id_finished_trip, actual_end_park, end_date) 
                VALUES(get_trip_id,temp_park_location,t_locking_date);   
    END IF;
    
    EXCEPTION 
    WHEN ex_trip_not_found THEN
    raise_application_error(-20019, 'Trip not found'); 
    
    WHEN ex_location_not_found THEN
    raise_application_error(-20020, 'Location not found'); 
    
    WHEN NO_DATA_FOUND THEN
    raise_application_error(-20021, 'Park or Location not found');     
END;
/
--=============================================================

--===========FUNCTION TO GET TRIP DATE=========================
CREATE OR REPLACE FUNCTION getTripDate(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
    temp_id_trip VARCHAR(50);
BEGIN
    SELECT t.id_trip INTO temp_id_trip
    FROM trip t, finished_trip ft
    WHERE t.id_trip = ft.id_finished_trip and 
          t.id_user = p_id_user;

    OPEN my_cursor FOR
        SELECT t.initial_date, ft.end_date
        FROM trip t, finished_trip ft
        WHERE t.id_trip = temp_id_trip;
    
    RETURN(my_cursor);
    
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20022, 'Trip not found'); 
END;
/
--=============================================================

--===========PROCEDURE TO LOCK VEHICLE COORD===================
CREATE OR REPLACE PROCEDURE lockVehicleCoord(p_idVehicle VARCHAR, p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER)
IS
    temp_location_id INTEGER;
    temp_id_park VARCHAR(50);
    temp_weight INTEGER;
BEGIN
    SELECT weight INTO temp_weight 
    FROM vehicles 
    WHERE id_vehicle = p_idVehicle;

    temp_location_id := getLocationID(p_latitude,p_longitude,p_elevation);
    
    SELECT id_park INTO temp_id_park
    FROM parks
    WHERE id_location = temp_location_id;
    
    UPDATE vehicles SET 
    id_location = temp_location_id 
    WHERE id_vehicle = p_idVehicle;
    
     EXCEPTION 
     WHEN NO_DATA_FOUND THEN
        raise_application_error(-20023, 'Park or Vehicle not found'); 
END;
/
--=============================================================

--===========PROCEDURE TO LOCK VEHICLE ID======================
CREATE OR REPLACE PROCEDURE lockVehicleID(p_idVehicle VARCHAR, p_idPark VARCHAR)
IS
    temp_location_id INTEGER;
    temp_weight INTEGER;
BEGIN
    SELECT weight INTO temp_weight 
    FROM vehicles 
    WHERE id_vehicle = p_idVehicle;
    
    SELECT id_location INTO temp_location_id 
    FROM parks 
    WHERE id_park = p_idPark;
    
    UPDATE vehicles SET 
    id_location = temp_location_id 
    WHERE id_vehicle = p_idVehicle;
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20024, 'Park or Vehicle not found'); 
END;
/
--=============================================================

--===========FUNCTION TO GET EMAIL=============================
CREATE OR REPLACE FUNCTION getEmail(p_idUser VARCHAR) RETURN VARCHAR 
IS
    client_email VARCHAR(100);
BEGIN
    SELECT email INTO client_email
    FROM "clients"
    WHERE id_user=p_idUser;
    RETURN(client_email);

    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
    raise_application_error(-20025, 'No client with that ID'); 
END;
/
--=============================================================

--===========FUNCTION TO GET ID FROM COORDS====================
CREATE OR REPLACE FUNCTION getIDFromCoords(p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER) RETURN VARCHAR
IS
    res_park_id VARCHAR(10);
    temp_location_id INTEGER;
BEGIN
    temp_location_id := getLocationID(p_latitude,p_longitude,p_elevation); 
    SELECT p.id_park INTO res_park_id
    FROM parks p
    WHERE p.id_location = temp_location_id;
    RETURN(res_park_id);

    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20026, 'No Park found'); 
END;
/
--=============================================================

--==============FUNCTION TO GET NULL LOCATION==================
CREATE OR REPLACE FUNCTION getNullLocation RETURN INTEGER
IS
    temp_location INTEGER;
BEGIN
    SELECT id_location INTO temp_location
    FROM locations
    WHERE latitude = 0 AND 
          longitude = 0 AND 
          elevation = 0;
    RETURN(temp_location);
    
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        INSERT INTO locations(latitude,longitude,elevation) VALUES (0,0,0);
        SELECT id_location INTO temp_location 
        FROM locations
        WHERE latitude = 0 AND 
              longitude = 0 AND 
              elevation = 0;
    RETURN(temp_location);
END;
/
--=============================================================

--==============PROCEDURE TO UNLOCK VEHICLE ID=================
CREATE OR REPLACE PROCEDURE unlockVehicleID(p_idVehicle VARCHAR)
IS
    temp_location_id INTEGER;
    temp_weight INTEGER;
BEGIN
    SELECT weight INTO temp_weight 
    FROM vehicles 
    WHERE id_vehicle = p_idVehicle;

    UPDATE vehicles SET 
    id_location = getNullLocation 
    WHERE id_vehicle = p_idVehicle;

    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20028, 'Vehicle not found'); 
END;
/
--=============================================================

--=======FUNCTION TO GET UNLOCK SCOOTER WITH MAX CAPACITY======
CREATE OR REPLACE FUNCTION getUnlockScooterWithMaxCapacity(p_idPark VARCHAR) RETURN VARCHAR 
IS
    temp_location_id INTEGER;
    temp_id VARCHAR(50);
BEGIN
    SELECT id_vehicle INTO temp_id
    FROM scooters 
    ORDER BY actual_battery_capacity DESC FETCH FIRST ROW ONLY;
    
    SELECT id_location INTO temp_location_id 
    FROM parks 
    WHERE id_park = p_idPark;

    UPDATE vehicles SET 
    id_location = getNullLocation 
    WHERE id_vehicle = temp_id;

    RETURN(temp_id);
     
    EXCEPTION WHEN NO_DATA_FOUND THEN
        raise_application_error(-20029, 'Vehicle not found'); 
END;
/
--=============================================================
--================FUNCTION TO CHECK CLIENT===================== 
CREATE OR REPLACE FUNCTION checkClient(p_user VARCHAR) RETURN INTEGER
IS
    res INTEGER;
BEGIN 
    SELECT COUNT(id_user) INTO res 
    FROM "clients" 
    WHERE id_user = p_user;
RETURN(res);    
END;
/
--=============================================================

--=============PROCEDURE TO ADD INVOICE LINE===================
CREATE OR REPLACE PROCEDURE addInvoiceLine(username VARCHAR, p_month INTEGER, p_year INTEGER, p_cost FLOAT)
IS
    temp_id INTEGER;
    check_user INTEGER;
    no_client EXCEPTION;
    a_actual_points INTEGER;
BEGIN
    check_user := checkClient(username);
    a_actual_points := addPoints(username);
    
    IF(check_user = 0 ) THEN
        RAISE no_client;
    ELSE

    SELECT id_invoice INTO temp_id 
    FROM invoice 
    WHERE id_user=username AND 
          "month"=p_month AND 
          "year" = p_year;
    
    INSERT INTO INVOICE_LINE(id_invoice,"cost",trip_points) 
        VALUES (temp_id,p_cost,a_actual_points);
 
    END IF;
        
    EXCEPTION WHEN NO_DATA_FOUND THEN
        INSERT INTO INVOICE(id_user,"month","year") VALUES (username,p_month,p_year);
        SELECT id_invoice INTO temp_id 
        FROM invoice
        WHERE id_user = username AND 
              "month" = p_month AND 
              "year" = p_year;
        INSERT INTO INVOICE_LINE(id_invoice,"cost",trip_points) VALUES (temp_id,p_cost,a_actual_points);

        WHEN no_client THEN
            raise_application_error(-20030, 'No user with this username in the system');  

END;
/
--=============================================================
 


--==============FUNCTION TO GENERATE INVOICE===================
CREATE OR REPLACE FUNCTION getMonthlyCostFromUser(username VARCHAR,mon INTEGER,yea INTEGER) RETURN FLOAT
IS
    invoice_cost FLOAT;
BEGIN
    SELECT SUM(l."cost") INTO invoice_cost 
    FROM invoice i, invoice_line l 
    WHERE i.id_user=username AND 
          i."month" = mon AND 
          i."year" = yea AND 
          i.id_invoice = l.id_invoice;
   
    UPDATE invoice SET 
    emission_date = SYSDATE 
    WHERE id_user = username AND 
          "month" = mon AND 
          "year" = yea;
         
    IF(invoice_cost IS NULL) THEN
        RETURN(0);
    ELSE
        RETURN(invoice_cost);
    END IF;
END;
/
--=============================================================

 

