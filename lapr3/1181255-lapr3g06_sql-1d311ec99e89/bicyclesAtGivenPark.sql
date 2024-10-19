CREATE OR REPLACE FUNCTION GETBICYCLESATGIVENPARK(p_latitude FLOAT, p_longitude FLOAT) RETURN INTEGER 
IS
bicycle_count INTEGER;
BEGIN

    SELECT count(*) INTO bicycle_count 
    FROM LOCATIONS INNER JOIN VEHICLES  ON LOCATIONS.id_location=VEHICLES.id_location
    Inner JOIN BICYCLES ON BICYCLES.id_vehicle=VEHICLES.id_vehicle
    WHERE latitude =p_latitude AND longitude=p_longitude;
    RETURN(bicycle_count);
    
     EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20005, 'Location not found');

END GETBICYCLESATGIVENPARK;
