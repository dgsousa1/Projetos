CREATE OR REPLACE PROCEDURE addPark (p_id_park VARCHAR, p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER,
p_description VARCHAR, p_max_bike int, p_max_scooter int,p_input_voltage number,
p_input_current number)
IS
    temp_location VARCHAR(10);
    loc INTEGER;
    ex_park_already_exists EXCEPTION;
BEGIN
    --if loc equals 1 that means that a park with that location already exists
    loc := checkLocation(p_latitude,p_longitude,p_elevation);
    
    if(loc=1) THEN
        RAISE ex_park_already_exists;
    ELSE
        temp_location := addLocation(p_latitude,p_longitude,p_elevation);
    END IF;
    
    INSERT INTO parks(id_park,id_location,"description",max_bike,max_scooter,input_voltage,input_current)
    VALUES(p_id_park,temp_location,p_description,p_max_bike,p_max_scooter,p_input_voltage,p_input_current);
    
    EXCEPTION WHEN ex_park_already_exists THEN
        raise_application_error(-20005, 'Park already exists');
END;
/

BEGIN
addPark('P010', 61.15227, 55.60929 , 1, 'Parque Teste' , 20,10,2,2);
END;
/


DROP PROCEDURE removePark;

CREATE OR REPLACE PROCEDURE removePark(p_id_park VARCHAR)
IS
    verification SYS_REFCURSOR;
    ex_park_not_found EXCEPTION;
BEGIN
    verification := getPark(p_id_park);
    --if the id givened as parameters does not exist
    IF(verification IS NULL) THEN
        RAISE ex_park_not_found;
    ELSE

    DELETE FROM parks WHERE id_park = p_id_park; 

    END IF;

    EXCEPTION 
    WHEN ex_park_not_found THEN
        raise_application_error(-20010, 'Park not found'); 
END;
/

CREATE OR REPLACE FUNCTION getPark(p_id_park VARCHAR) RETURN SYS_REFCURSOR
IS
    cp SYS_REFCURSOR;
BEGIN
    OPEN cp FOR
        SELECT p.id_park, l.latitude,l.longitude,l.elevation,p."description",p.max_bike,p.max_scooter,p.input_voltage,p.input_current
        FROM parks p, locations l
        WHERE p.id_location = l.id_location and        
        p.id_park = p_id_park;
    RETURN(cp);
END;
/

CREATE OR REPLACE FUNCTION getAllParks RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        SELECT p.id_park,l.latitude,l.longitude,p."description",p.max_bike,p.max_scooter,p.input_voltage,p.input_current
        FROM parks p,locations l
        WHERE p.id_location = l.id_location;
        
    return(my_cursor);
END;
/
--    select * from parks;
--    select * from locations;
--    
--    INSERT INTO locations values(60.5,44.3,12);

CREATE OR REPLACE PROCEDURE updatePark(p_idPark VARCHAR,new_latitude FLOAT, new_longitude FLOAT, new_elevation INTEGER,
new_description VARCHAR, new_maxBike INTEGER, new_maxScooter INTEGER, new_inputVoltage FLOAT, new_inputCurrent FLOAT)
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
        "description" = new_description , id_location = temp_location, max_bike=new_maxBike , max_scooter=new_maxScooter,
        input_voltage = new_inputVoltage , input_current = new_inputCurrent
        WHERE id_park = p_idPark;

    END IF;

        EXCEPTION 
        WHEN ex_park_not_found THEN
        raise_application_error(-20010, 'Park not found'); 
END;
/

--BEGIN
--updatePark('P001',45.38221,71.963841,5,'Parque Noroeste',15,20,3.45,7.25);
--END;
--/

--***********************CHECK FREE PARKING SCOOTER*****************************
DROP FUNCTION checkFreeScooterPlaces;

CREATE OR REPLACE FUNCTION checkFreeScooterPlaces(p_idPark VARCHAR) RETURN INTEGER
IS
    nr_free_places INTEGER;
    temp_location INTEGER;
BEGIN
    SELECT id_location into temp_location 
    from parks 
    where id_park = p_idPark;
    
    select p.max_scooter-(select count(s.id_vehicle) 
                            from vehicles v, scooters s
                            where v.id_location = temp_location 
                            and v.id_vehicle = s.id_vehicle) INTO nr_free_places
    FROM parks p
    WHERE p.id_location = temp_location;
    
    return(nr_free_places);
END;
/

set serverout on;
DECLARE
    variavel integer;
BEGIN
    variavel:=checkFreeScooterPlaces('P001');
    dbms_output.put_line('Availabel Scooter slots: ' || variavel);      
END;
/
--***********************CHECK FREE PARKING BICYCLES*****************************
DROP FUNCTION checkFreeBicyclesPlaces;

CREATE OR REPLACE FUNCTION checkFreeBicyclesPlaces(p_idPark VARCHAR) RETURN INTEGER
IS
    nr_free_places INTEGER;
    temp_location INTEGER;
BEGIN
    SELECT id_location into temp_location 
    from parks 
    where id_park = p_idPark;
    
    select p.max_bike-(select count(b.id_vehicle) 
                            from vehicles v, bicycles b
                            where v.id_location = temp_location 
                            and v.id_vehicle = b.id_vehicle) INTO nr_free_places
    FROM parks p
    WHERE p.id_location = temp_location;
    
    return(nr_free_places);
END;
/

set serverout on;
DECLARE
    variavel integer;
BEGIN
    variavel:=checkFreeBicyclesPlaces('P002');
    dbms_output.put_line('Availabel Bicycles slots: ' || variavel);      
END;
/
--*************************************************
CREATE OR REPLACE FUNCTION getAllLocations RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        select l.latitude,l.longitude, l.elevation
        from locations l
        where l.id_location in 
        (select id_location from parks) or 
        id_location in (select id_location from poi);
    return(my_cursor);
END;
/

DECLARE
    my_cursor sys_Refcursor;
    a_latitude float;
    a_longitude float;
    a_elevation int;
BEGIN
    my_cursor := getAllLocation();
    if(my_cursor is not null) then
        loop
            fetch my_cursor into a_latitude, a_longitude, a_elevation;
            exit when my_cursor%notfound;
            dbms_output.put_line('Latitude: '|| a_latitude || 'Longitude: ' || a_longitude || 'Elevation: ' || a_elevation);
        end loop;
        else
        dbms_output.put_line('NULL');
    END IF;       
END;
/
--==========================================
CREATE OR REPLACE FUNCTION getReportForTheAdminOfAPark(p_id_park VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR
        SELECT p.id_park,p.max_scooter,p.input_voltage, p.input_current, v.id_vehicle, v.weight, l.latitude, l.longitude, v.aerodynamic_coef, 
               v.frontal_area, s.max_battery_capacity, s.actual_battery_capacity, s."type", s."power"
        FROM vehicles v, locations l, scooters s, parks p
        WHERE p.id_park = p_id_park AND
              p.id_location = v.id_location AND
              v.id_location = l.id_location AND
              v.id_vehicle = s.id_vehicle;  
    RETURN(my_cursor);
END;
/
--==========================================

    



--==========================================










