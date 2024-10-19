CREATE OR REPLACE FUNCTION checkLocation(p_latitude FLOAT, p_longitude FLOAT , p_elevation INTEGER) RETURN 
INTEGER
IS
summ INTEGER;
BEGIN 
    
    SELECT COUNT(id_location) INTO summ
    FROM locations
    WHERE latitude = p_latitude AND longitude = p_longitude AND elevation = p_elevation;  
    RETURN(summ);
END;
/


SET SERVEROUT ON;
DECLARE
res INTEGER;
BEGIN
res := checkLocation(30,31,0);
dbms_output.put_line('res: ' || res);
END;
 


CREATE OR REPLACE PROCEDURE addPath(a_latitude FLOAT , a_longitude FLOAT , a_elevation INTEGER ,
b_latitude FLOAT , b_longitude FLOAT , b_elevation INTEGER , kin_coeff FLOAT , w_dir FLOAT , w_speed FLOAT)
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
        
       SELECT l2.id_location INTO temp_location  FROM locations l2 WHERE l2.id_location = id_location_a AND l2.id_location IN (
        SELECT UNIQUE  l.id_location 
        FROM locations l , parks p , POI o 
        WHERE l.id_location = p.id_location OR l.id_location = o.id_location);

    ELSE     
        RAISE already_exists;
    END IF;
    
    IF(res_b = 1) THEN
        id_location_b := getLocationID(b_latitude,b_longitude,b_elevation);
        
       SELECT l2.id_location INTO temp_location  FROM locations l2 WHERE l2.id_location = id_location_b AND l2.id_location IN (
        SELECT UNIQUE  l.id_location 
        FROM locations l , parks p , POI o 
        WHERE l.id_location = p.id_location OR l.id_location = o.id_location);
    ELSE     
        RAISE already_exists;
    END IF;
    
    
    
    INSERT INTO Paths("start","end",kinetic_coefficient,wind_direction,wind_speed) VALUES (id_location_a,id_location_b,kin_coeff,w_dir,w_speed);
     
    EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20005, 'Location not found'); 
    
    WHEN already_exists THEN
    raise_application_error(-20004, 'Location already exists in the system'); 
END;
/


SET SERVEROUT ON;
DECLARE
BEGIN
addPath(20 , 45.60929, 0 ,
61.15227 , 70 , 0 , 1 , 2 , 3);
END;


CREATE OR REPLACE FUNCTION getAllPaths RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR    
        SELECT LA.latitude,LA.longitude,LA.elevation,
               LB.latitude,LB.longitude,LB.elevation, p.kinetic_coefficient,p.wind_direction,p.wind_speed
        FROM locations LA, locations LB, paths p
        WHERE LA.id_location = p."start" AND LB.id_location = p."end";        
    RETURN(my_cursor);
END;
/


SET SERVEROUT ON;
DECLARE
    report_history SYS_REFCURSOR;   
    latitude_start FLOAT;
    longitude_start FLOAT;
    elevation_start INTEGER;
    
    latitude_end FLOAT;
    longitude_end FLOAT;
    elevation_end INTEGER;    
    kin_coeff NUMBER(5,3);
    wind_dir NUMBER(4,1);
    wind_speed NUMBER(4,1);
   
BEGIN
    report_history:=getAllPaths;
    IF(report_history IS NOT NULL) THEN
            LOOP
                FETCH report_history INTO latitude_start,longitude_start,elevation_start,
                                          latitude_end,longitude_end,elevation_end,
                                            kin_coeff, wind_dir,wind_speed;
                EXIT WHEN report_history%notfound;
                dbms_output.put_line(
                ' LAT START: ' || latitude_start || ' LONG START: ' || longitude_start || ' ELEV START: ' || elevation_start || 
                ' LAT END: ' || latitude_end || ' LONG END: ' || longitude_end || ' ELEV END: ' || elevation_end ||                
                ' KINCOEFF: ' || kin_coeff || ' WIND DIRECTION: ' || wind_dir  || ' WIND SPEED: ' || wind_speed);
            END LOOP;
        ELSE
        dbms_output.put_line('NULL');
    END IF;
END;
/


 
