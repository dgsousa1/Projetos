CREATE OR REPLACE FUNCTION getEmail(p_idUser VARCHAR) RETURN VARCHAR 
IS
client_email VARCHAR(100);

BEGIN
SELECT email INTO client_email
FROM "clients"
WHERE id_user=p_idUser;
RETURN(client_email);

EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20030, 'No client with that ID'); 
END;
/

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

EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20034, 'No Park found'); 
END;
/

/*
SET SERVEROUT ON;
DECLARE
res VARCHAR(10);
BEGIN 
--res := getLocationID(61.15227,55.60929,1);
res := getIDFromCoords(61.15227,55.60929,1);
dbms_output.put_line('res: ' || res);
END;
/
*/

SET SERVEROUT ON;
DECLARE
res VARCHAR(100);            
BEGIN
res := getEmail('idUser');
dbms_output.put_line('res: ' || res);
END;
/

CREATE OR REPLACE FUNCTION getIDFromCoordsWithoutElevation(p_latitude FLOAT, p_longitude FLOAT) RETURN VARCHAR
IS
res_park_id VARCHAR(10);
temp_location_id INTEGER;

BEGIN
SELECT p.id_park INTO res_park_id
FROM parks p
INNER JOIN locations l
ON p.id_location = l.id_location
WHERE l.latitude = p_latitude AND l.longitude = p_longitude;
RETURN(res_park_id);

EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20034, 'No Park found'); 
END;
/

CREATE OR REPLACE FUNCTION getNullLocation RETURN INTEGER
IS
temp_location INTEGER;
BEGIN
SELECT id_location INTO temp_location
FROM locations
WHERE latitude = 0 AND longitude = 0 AND elevation = 0;
RETURN(temp_location);
 EXCEPTION WHEN NO_DATA_FOUND THEN
        INSERT INTO locations(latitude,longitude,elevation) VALUES (0,0,0);
        SELECT id_location INTO temp_location 
        FROM locations
        WHERE latitude = 0 AND longitude = 0 AND elevation = 0;
        RETURN(temp_location);
END;
/

--DECLARE
--res INTEGER;
--BEGIN
--res := getNullLocation;
--dbms_output.put_line('res: ' || res);
--END;
--/
