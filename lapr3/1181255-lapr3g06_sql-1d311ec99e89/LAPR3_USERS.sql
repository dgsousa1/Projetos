CREATE OR REPLACE PROCEDURE addClient(id_user VARCHAR, pass VARCHAR, email VARCHAR, credit_card VARCHAR, height INTEGER, weight INTEGER, 
                                                        gender VARCHAR, cycling_average_speed FLOAT, points INTEGER) 
IS

BEGIN
        INSERT INTO "users"(id_user, pass) VALUES (id_user, pass);
        INSERT INTO "clients"(id_user, email, weight, credit_card, height,cycling_average_speed,gender,points) VALUES (id_user, email, weight, credit_card, height,cycling_average_speed,gender,points);
END;
/

BEGIN
addClient('2A','abc','adadk2@email.com','19992',2,3,'mAn',2.9);
END;
/


CREATE OR REPLACE FUNCTION getClient(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    v_rc SYS_REFCURSOR;
BEGIN
    OPEN v_rc FOR
    SELECT c.id_user, u.pass, c.email, c.credit_card, c.height, c.weight, c.gender, c.cycling_average_speed, c.points
    FROM "clients" c, "users" u
    WHERE u.id_user = c.id_user and
          c.id_user = p_id_user;
    
    RETURN(v_rc);
END;
/
--=============================================================
CREATE OR REPLACE FUNCTION checkIfTravel(p_id_user VARCHAR) RETURN INTEGER
IS
    trips INTEGER;   
BEGIN
    SELECT COUNT(t.id_trip) INTO trips
    FROM trip_requests tr, trip t
    WHERE tr.id_trip = t.id_trip AND tr.id_user = p_id_user;
    RETURN(trips);
END;
/

SET SERVEROUT ON;
declare
    m integer;
begin
    m:=checkIfTravel('user1');
    dbms_output.put_line(m);    
end;
/
--=============================================================
CREATE OR REPLACE FUNCTION getPointsOfTrip(p_id_user VARCHAR) RETURN SYS_REFCURSOR
IS
    my_cursor SYS_REFCURSOR;
    trip_user_verify INTEGER;
    ex_user_without_trip EXCEPTION;
BEGIN
    trip_user_verify:=checkIfTravel(p_id_user);
    IF(trip_user_verify=0) THEN
        RAISE ex_user_without_trip;
    ELSE
        OPEN my_cursor FOR
            SELECT LA.elevation A,LB.elevation B
            FROM trip_requests tr, locations LA, locations LB, trip t
        WHERE LA.id_location = (SELECT l.id_location 
                                FROM locations l, parks p 
                                WHERE l.id_location = p.id_location AND 
                                p.id_park = tr.id_park) AND
              LB.id_location = (SELECT l.id_location
                                FROM locations l
                                WHERE l.id_location = tr.id_location) 
        AND tr.id_user = p_id_user AND tr.id_trip = t.id_trip;
    RETURN(my_cursor);
    END IF;

    EXCEPTION 
    WHEN ex_user_without_trip THEN
        raise_application_error(-20012, 'User with all trip points updated or with no trips');     
END;
/

SET SERVEROUT ON;
DECLARE
    c SYS_REFCURSOR;
    elevationA FLOAT;    
    elevationB FLOAT;
BEGIN
    c:=updatePoints('user1');
    IF(c IS NOT NULL) THEN
            LOOP
                FETCH c INTO elevationA, elevationB;
                EXIT WHEN c%notfound;
                dbms_output.put_line('ELEVATION A:  ' || elevationA || ' ELEVATION B: ' || elevationB);
            END LOOP;
        ELSE
        dbms_output.put_line('NULL');
    END IF;
END;
/

select * from "clients";

select * from trip_requests;
--=============================================================
CREATE OR REPLACE PROCEDURE addPoints(p_id_user VARCHAR,t_elevationA INTEGER, t_elevationB INTEGER)
IS
    temp_points INTEGER;
    temp_id_trip INTEGER;
    dif_elevation INTEGER;
    ex_not_qualified_for_points EXCEPTION;
BEGIN
    dif_elevation:=t_elevationB-t_elevationA;
    
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
    points = points + temp_points
    WHERE id_user = p_id_user;
    
    SELECT tr.id_trip INTO temp_id_trip
    FROM trip_requests tr, trip t
    WHERE tr.id_trip = t.id_trip and tr.id_user = p_id_user;
    
    DELETE 
    FROM trip 
    WHERE id_trip = temp_id_trip;

    EXCEPTION
    WHEN ex_not_qualified_for_points THEN
        raise_application_error(-20012, 'Not qualified to get points');    
END;
/
--=============================================================
select * from "clients";
select * from trip;
select * from trip_requests;



CREATE OR REPLACE FUNCTION getPointsFromUser(p_id_user VARCHAR) RETURN INTEGER
IS
    a_points INTEGER;
BEGIN

    SELECT c.points INTO a_points
    FROM "clients" c
    WHERE c.id_user = p_id_user;
    
    RETURN(a_points);
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
    raise_application_error(-20016, 'Id user not found');
END;
/

CREATE OR REPLACE PROCEDURE setPoints(p_id_user VARCHAR,p_points INTEGER)
IS
    t_id_user VARCHAR(10);
BEGIN
    
   SELECT id_user INTO t_id_user FROM "clients" WHERE id_user = p_id_user; 
   
    UPDATE "clients" SET
    points = p_points
    WHERE id_user = p_id_user;
    
    
    EXCEPTION WHEN NO_DATA_FOUND THEN
    raise_application_error(-20016, 'Id user not found');  
END;
/


