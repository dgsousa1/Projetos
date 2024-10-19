CREATE OR REPLACE PROCEDURE addBicycle(idVehicle VARCHAR, weight INTEGER, param_latitude FLOAT, param_longitude FLOAT, 
                                                        aerodynamicCoef FLOAT, frontalArea FLOAT, wheelSize FLOAT) 
IS
    temp_location VARCHAR(10);
BEGIN
    SELECT id_location INTO temp_location FROM locations l WHERE l.latitude = param_latitude AND l.longitude = param_longitude;
    
    INSERT INTO vehicles(id_vehicle, id_location, weight, frontal_area, aerodynamic_coef) VALUES (idVehicle, temp_location, weight, frontalArea, aerodynamicCoef);
    INSERT INTO bicycles(id_vehicle, wheel_size) VALUES (idVehicle, wheelSize);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
       raise_application_error(-20000, 'Location doesnt exist in the system');
END;
/

CREATE OR REPLACE PROCEDURE addScooter(idVehicle VARCHAR, weight INTEGER, param_latitude FLOAT, param_longitude FLOAT, 
                                                        aerodynamicCoef FLOAT, frontalArea FLOAT, maxBatteryCapacity FLOAT, actualBatteryCapaity FLOAT, param_type String, p_power FLOAT) 
IS
    temp_location VARCHAR(10);
BEGIN
    SELECT id_location INTO temp_location FROM locations l WHERE l.latitude = param_latitude AND l.longitude = param_longitude;
    
    INSERT INTO vehicles(id_vehicle, id_location, weight, frontal_area, aerodynamic_coef) VALUES (idVehicle, temp_location, weight, frontalArea, aerodynamicCoef);
    INSERT INTO scooters(id_vehicle, max_battery_capacity, actual_battery_capacity, "type","power") VALUES (idVehicle, maxBatteryCapacity, actualBatteryCapaity, param_type,p_power);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
       raise_application_error(-20001, 'Location doesnt exist in the system');
END;
/

CREATE OR REPLACE FUNCTION getBicycle(idVehicle VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, v.frontal_area, b.wheel_size  
    FROM vehicles v
    INNER JOIN locations l
    ON v.id_location = l.id_location
    INNER JOIN bicycles b
    ON v.id_vehicle = b.id_vehicle
    WHERE v.id_vehicle = idVehicle;
    
    RETURN(v_rc);
END;
/

CREATE OR REPLACE FUNCTION getScooter(idVehicle VARCHAR) RETURN SYS_REFCURSOR
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
    WHERE v.id_vehicle = idVehicle;
       
    RETURN(v_rc);
END;
/

DROP PROCEDURE removeBicycle;

CREATE OR REPLACE PROCEDURE removeBicycle(p_idVehicle VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_bycicle_not_found EXCEPTION;
BEGIN
    verification := getBicycle(p_idVehicle);
    --if the id givened as parameters does not exist
    IF(verification IS NULL) THEN
        RAISE ex_bycicle_not_found;
    ELSE
    
    DELETE FROM bicycles WHERE id_vehicle = p_idVehicle;
    DELETE FROM vehicles WHERE id_vehicle = p_idVehicle;   
        
    END IF;
    
    EXCEPTION 
    WHEN ex_bycicle_not_found THEN
        raise_application_error(-20005, 'Bicycle not found'); 
END;
/

DROP PROCEDURE removeScooter;

CREATE OR REPLACE PROCEDURE removeScooter(p_idVehicle VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_scooter_not_found EXCEPTION;
BEGIN
    verification := getScooter(p_idVehicle);
    --if the id givened as parameters does not exist
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

DROP PROCEDURE updateBicycle;

--receive as parameters an id for the bicycle to search for and all the new atributed
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
    --if the id givened as parameters does not exist
    IF(verificationBike IS NULL) THEN
        RAISE ex_bicycle_not_found;
    ELSE
        verificationLocation := checkLocation(new_latitude,new_longitude,0);
        --if verificationLocation equals 1 location exists
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
        raise_application_error(-20005, 'Bicycle not found'); 
        
        WHEN ex_locationPark_not_found THEN
        raise_application_error(-20006, 'Park location not found'); 
END;
/

DROP PROCEDURE updateScooter;

--receive as parameters an id for the scooter to search for and all the new atributed
CREATE OR REPLACE PROCEDURE updateScooter(p_idVehicle VARCHAR,new_weight INTEGER,new_latitude FLOAT, new_longitude FLOAT,
new_aerodynamicCoef FLOAT, new_frontalArea FLOAT,new_max_battery_capacity FLOAT, new_actual_battery_capacity FLOAT, new_type VARCHAR, new_power FLOAT)
IS
    verificationScooter SYS_REFCURSOR;
    ex_scooter_not_found EXCEPTION;
    ex_locationPark_not_found EXCEPTION;
    verificationLocation INTEGER;
    new_location VARCHAR(10); 
BEGIN
    verificationScooter := getScooter(p_idVehicle);
    --if the id givened as parameters does not exist
    IF(verificationScooter IS NULL) THEN
        RAISE ex_scooter_not_found;
    ELSE
        verificationLocation := checkLocation(new_latitude,new_longitude,0);
        --if verificationLocation equals 1 location exists
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
        raise_application_error(-20005, 'Scooter not found'); 
        
        WHEN ex_locationPark_not_found THEN
        raise_application_error(-20006, 'Park location not found'); 
END;
/


CREATE OR REPLACE FUNCTION getAllScootersOfPark(p_latitude FLOAT,p_longitude FLOAT) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
    temp_park_location INTEGER;
    t_id_park VARCHAR(10);
BEGIN
    --temp_park_location
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

SET SERVEROUT ON;
DECLARE
    report_history SYS_REFCURSOR;
    id_vehicle VARCHAR(10);
    weight INTEGER;    
    latitude FLOAT;
    longitude FLOAT;
    aero NUMBER(3,2);
    frontal NUMBER(2,1);
    max_battery NUMBER(4,2);    
    actual INTEGER;
    typee VARCHAR(10); 
    ppower FLOAT;
    
BEGIN
    report_history:=getAllScootersOfPark(61.15227,70);
    IF(report_history IS NOT NULL) THEN
            LOOP
                FETCH report_history INTO id_vehicle,weight,latitude,longitude,aero,frontal,max_battery,actual,typee,ppower;
                EXIT WHEN report_history%notfound;
                dbms_output.put_line(' ID VEHICLE: ' || id_vehicle || ' WEIGHT: ' || weight ||
                ' LAT: ' || latitude || ' LONG: ' || longitude|| ' AERODYNAMIC: ' || aero || 
                ' FRONTAL AREA: ' || frontal || ' MAX BATTERY: ' || max_battery || ' ACTUAL BATTERY: ' || actual ||                
                ' TYPE: ' || typee || ' POWER: ' || ppower);
            END LOOP;
        ELSE
        dbms_output.put_line('NULL');
    END IF;
END;
/


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

CREATE OR REPLACE FUNCTION getBicycleFromSpecs(vehicleSpecs VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, v.frontal_area, b.wheel_size  
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






