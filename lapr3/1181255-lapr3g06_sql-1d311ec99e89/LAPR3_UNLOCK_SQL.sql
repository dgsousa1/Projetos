CREATE OR REPLACE PROCEDURE unlockVehicleID(p_idVehicle VARCHAR)
IS
temp_location_id INTEGER;
temp_weight INTEGER;
BEGIN
    SELECT weight INTO temp_weight FROM vehicles WHERE id_vehicle = p_idVehicle;

    UPDATE vehicles SET id_location = getNullLocation WHERE id_vehicle = p_idVehicle;

     EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20022, 'Vehicle not found'); 
END;

exec unlockVehicleID('S001');


CREATE OR REPLACE FUNCTION getUnlockScooterWithMaxCapacity(p_idPark VARCHAR) RETURN VARCHAR 
IS

temp_location_id INTEGER;
temp_id VARCHAR(10);
BEGIN
    select id_vehicle into temp_id
    from scooters 
    order by actual_battery_capacity DESC fetch first row only;
    
    SELECT id_location INTO temp_location_id FROM parks WHERE id_park = p_idPark;

    UPDATE vehicles SET id_location = getNullLocation WHERE id_vehicle = temp_id;

    RETURN(temp_id);
     
     EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20022, 'Vehicle not found'); 
END;
/

