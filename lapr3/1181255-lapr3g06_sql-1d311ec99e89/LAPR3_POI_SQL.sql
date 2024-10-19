CREATE OR REPLACE FUNCTION addLocation(p_latitude FLOAT , p_longitude FLOAT , p_elevation INTEGER) RETURN 
INTEGER 
IS
temp_location INTEGER;
already_exists EXCEPTION;
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations
    WHERE latitude =p_latitude AND longitude=p_longitude AND elevation = p_elevation;
    
    RAISE already_exists;
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
        INSERT INTO locations(latitude,longitude,elevation) VALUES (p_latitude,p_longitude,p_elevation);
        SELECT id_location INTO temp_location 
        FROM locations
        WHERE latitude =p_latitude AND longitude=p_longitude AND elevation = p_elevation;
        RETURN(temp_location);

    WHEN already_exists THEN
    raise_application_error(-20004, 'Location already exists in the system'); 

END;    
/

CREATE OR REPLACE PROCEDURE addPOI(p_latitude FLOAT , p_longitude FLOAT , p_elevation INTEGER , p_description VARCHAR)
IS
id_location INTEGER;
BEGIN
    id_location := addLocation(p_latitude,p_longitude,p_elevation);
    INSERT INTO POI(id_location,"description") VALUES (id_location,p_description);
END;
/
/*
SET SERVEROUT ON;
BEGIN
addPOI(90,80,0,'desc de test');
--addPOI(40,40,0,'desc de test');

END;*/

CREATE OR REPLACE FUNCTION getLocationID(p_latitude NUMBER, p_longitude NUMBER, p_elevation INTEGER) RETURN INTEGER
IS
temp_location INTEGER;
BEGIN
    SELECT id_location INTO temp_location 
    FROM locations
    WHERE latitude =p_latitude AND longitude=p_longitude AND elevation=p_elevation;
    RETURN(temp_location);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20005, 'Location not found'); 
END;    
/

/*
SET SERVEROUT ON;
DECLARE
res INTEGER;
BEGIN
res := getLocationID(61.15227, 55.60929,1); --should work
--res := getLocationID(10,10); --should fail
dbms_output.put_line('res: ' || res);
END;*/


CREATE OR REPLACE FUNCTION getPOI(p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER) RETURN SYS_REFCURSOR
IS 
poi_cursor SYS_REFCURSOR;
temp_location_id INTEGER;
BEGIN
    temp_location_id := getLocationID(p_latitude,p_longitude, p_elevation);
    OPEN poi_cursor FOR
    SELECT p."description" , l.latitude, l.longitude, l.elevation
    FROM poi p , locations l 
    WHERE p.id_location = temp_location_id AND p.id_location=l.id_location;
    RETURN(poi_cursor);
    
END;


DECLARE
    poi_cursor sys_refcursor;
    p_description VARCHAR(200);
    p_latitude FLOAT;
    p_longitude FLOAT;
    p_elevation INTEGER;
BEGIN
    poi_cursor:=getPOI(90,80); --should work
    --poi_cursor:=getPOI(70,80); --should fail
    IF(poi_cursor IS NOT NULL) THEN 
        LOOP
            FETCH poi_cursor INTO p_description, p_latitude, p_longitude , p_elevation;
            EXIT WHEN poi_cursor%notfound;
            dbms_output.put_line('p_description: ' || p_description);
            dbms_output.put_line('p_latitude: ' || p_latitude);
            dbms_output.put_line('p_longitude: ' || p_longitude);
            dbms_output.put_line('p_elevation: ' || p_elevation);
        END LOOP;
    ELSE
    dbms_output.put_line('NULL');
    END IF;
    --CLOSE v_rc;
END;
