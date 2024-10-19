--=============================================================
CREATE OR REPLACE FUNCTION getUserTripReport(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR  
        SELECT tr.id_trip, tr.id_vehicle, tr.id_user,LA.latitude,LA.longitude,LA.elevation,
                                             LB.latitude,LB.longitude,LB.elevation, tr.initial_date UNLOCKING_DATE,tr.end_date LOCKING_DATE
        FROM trip_requests tr, locations LA, locations LB
        WHERE LA.id_location = (SELECT l.id_location 
                       FROM locations l, parks p 
                       WHERE l.id_location = p.id_location AND 
                             p.id_park = tr.id_park) AND
              LB.id_location = (SELECT l.id_location
                        FROM locations l
                        WHERE l.id_location = tr.id_location) AND tr.id_user = p_id_user;
    RETURN(my_cursor);
END;
/

SET SERVEROUT ON;
DECLARE
    report_history SYS_REFCURSOR;
    id_trip INTEGER;    
    id_vehicle VARCHAR(10);
    id_user VARCHAR(10);    
    latitude_start FLOAT;
    longitude_start FLOAT;
    elevation_start INTEGER;
    
    latitude_end FLOAT;
    longitude_end FLOAT;
    elevation_end INTEGER;    
    unlocking_date DATE;
    locking_date DATE; 
    
BEGIN
    report_history:=getAllTrips('user1');
    IF(report_history IS NOT NULL) THEN
            LOOP
                FETCH report_history INTO id_trip,id_vehicle,id_user,latitude_start,longitude_start,elevation_start,
                                          latitude_end,longitude_end,elevation_end,
                                          unlocking_date,locking_date;
                EXIT WHEN report_history%notfound;
                dbms_output.put_line('ID TRIP: ' || id_trip || ' ID VEHICLE: ' || id_vehicle || ' ID USER: ' || id_user ||
                ' LAT START: ' || latitude_start || ' LONG START: ' || longitude_start || ' ELEV START: ' || elevation_start || 
                ' LAT END: ' || latitude_end || ' LONG END: ' || longitude_end || ' ELEV END: ' || elevation_end ||                
                ' UNLOCKING DATE: ' || unlocking_date || ' LOCKING DATE: ' || locking_date);
            END LOOP;
        ELSE
        dbms_output.put_line('NULL');
    END IF;
END;
/
--=============================================================
CREATE OR REPLACE PROCEDURE addTrip (t_id_vehicle VARCHAR, t_latitude FLOAT, t_longitude FLOAT, t_elevation INTEGER,
t_id_user VARCHAR,t_unlocking_date DATE)
IS
    temp_location_id INTEGER;
    temp_id_park VARCHAR(10);
BEGIN
    temp_location_id := getLocationID(t_latitude,t_longitude);
    SELECT id_park INTO temp_id_park
    FROM parks
    WHERE id_location = temp_location_id;

    INSERT INTO trip_requests(id_vehicle,id_park,id_user,initial_date)
    VALUES(t_id_vehicle,temp_id_park,t_id_user,t_unlocking_date);

    EXCEPTION WHEN NO_DATA_FOUND THEN
    raise_application_error(-20022, 'Park or Vehicle not found');

END;
/


EXEC addTrip('B001',30,30,0,'user3',to_Date('14/10/2019 14:30','dd/mm/yyyy hh24:mi'));
select * from trip_requests;


--=============================================================
CREATE OR REPLACE FUNCTION getTripId(t_id_vehicle VARCHAR,t_id_park VARCHAR,t_id_user VARCHAR,t_initial_date DATE) RETURN INTEGER
IS
    a_id_trip INTEGER;
BEGIN
        SELECT id_trip INTO a_id_trip
        FROM trip_requests
        WHERE id_vehicle = t_id_vehicle AND
              t_id_park = id_park AND
              t_id_user = id_user AND
              t_initial_date = initial_date;
    return(a_id_trip);
END;
/

CREATE OR REPLACE PROCEDURE updateTrip(t_id_vehicle VARCHAR,t_id_park VARCHAR,t_id_user VARCHAR, t_unlocking_date DATE,
new_latitude FLOAT, new_longitude FLOAT, new_elevation INTEGER, t_locking_date DATE)
IS
    get_trip_id INTEGER;
    ex_trip_not_found EXCEPTION;
    ex_location_not_found EXCEPTION;
    verificationLocation INTEGER;
    temp_location_id INTEGER;
    temp_id_park VARCHAR(10);
BEGIN
    --RETURN THE ID OF THE TRIP WITH THE PARAMETERS
    get_trip_id := getTripId(t_id_vehicle,t_id_park,t_id_user,t_unlocking_date);

    IF(get_trip_id IS NULL) THEN
        RAISE ex_trip_not_found;

    ELSE
    --RETURN 0 IF NOT EXISTS
        verificationLocation := checkLocation(new_latitude,new_longitude,new_elevation);
        
        IF(verificationLocation=0) THEN
            RAISE ex_location_not_found;
        ELSE
            temp_location_id := getLocationID(new_latitude,new_longitude,new_elevation);
            
            SELECT id_park INTO temp_id_park
            FROM parks
            WHERE id_location = temp_location_id;
            
            UPDATE trip_requests SET
            id_location = temp_location_id, end_date = t_locking_date
            WHERE id_trip = get_trip_id;  
        END IF;
        
    END IF;
    
    EXCEPTION 
    WHEN ex_trip_not_found THEN
    raise_application_error(-20010, 'Trip not found'); 
    
    WHEN ex_location_not_found THEN
    raise_application_error(-20011, 'Location not found'); 
    
    WHEN NO_DATA_FOUND THEN
    raise_application_error(-20012, 'Park or Location not found');     
END;
/
alter session set nls_date_format = 'DD-MM-YYYY HH24:MI';
UPDATE trip_requests SET id_location = NULL WHERE id_park='Teste4';
UPDATE trip_requests SET end_date = NULL WHERE id_park='Teste4';
SELECT * FROM trip_requests;
SELECT * FROM locations;
SELECT * FROM parks;
BEGIN
updateTrip('S001','Teste4','idUser',TO_DATE('24/12/19 15:30' , 'dd/mm/yy hh24:mi'),
61.15227, 55.60929, 104, TO_DATE('24/12/19 20:30' , 'dd/mm/yy hh24:mi'));
END;
/
--=============================================================
CREATE OR REPLACE FUNCTION getTripDate(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
    temp_id_trip VARCHAR(10);
BEGIN
    SELECT tr.id_trip INTO temp_id_trip
    FROM trip_requests tr, trip t
    WHERE tr.id_trip = t.id_trip and tr.id_user = p_id_user;

    OPEN my_cursor FOR
        SELECT tr.initial_date, t.end_date
        FROM trip_requests tr, trip t
        WHERE tr.id_trip = temp_id_trip;
    return(my_cursor);
    
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
    raise_application_error(-20012, 'Trip not found'); 
END;
/

SET SERVEROUT ON;
declare
    m sys_refcursor;
    start_date DATE;
    end_date DATE;
begin
    m:=getTripDate('idUser');
    IF(m IS NOT NULL) THEN
        LOOP
            FETCH m into start_date, end_date;
            EXIT WHEN m%notfound;
        dbms_output.put_line('Start date: ' || start_date || ' End date: ' || end_date);
        END LOOP;
    ELSE
        dbms_output.put_line('NULL');    
    END IF; 
end;
/
--=============================================================
