CREATE OR REPLACE PROCEDURE lockVehicleCoord(p_idVehicle VARCHAR, p_latitude FLOAT, p_longitude FLOAT, p_elevation INTEGER)
IS
temp_location_id INTEGER;
temp_id_park VARCHAR(10);
temp_weight INTEGER;
BEGIN
    SELECT weight INTO temp_weight FROM vehicles WHERE id_vehicle = p_idVehicle;

    temp_location_id := getLocationID(p_latitude,p_longitude,p_elevation);
    SELECT id_park INTO temp_id_park
    FROM parks
    WHERE id_location = temp_location_id;
    
    UPDATE vehicles SET id_location = temp_location_id WHERE id_vehicle = p_idVehicle;
    
    
     EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20022, 'Park or Vehicle not found'); 
END;

/*
BEGIN
lockVehicleCoord('S002',65.15227,45.60929);
END;
*/ 
    
CREATE OR REPLACE PROCEDURE lockVehicleID(p_idVehicle VARCHAR, p_idPark VARCHAR)
IS
temp_location_id INTEGER;
temp_weight INTEGER;
BEGIN

    SELECT weight INTO temp_weight FROM vehicles WHERE id_vehicle = p_idVehicle;
    
    SELECT id_location INTO temp_location_id FROM parks WHERE id_park = p_idPark;
    
    
    UPDATE vehicles SET id_location = temp_location_id WHERE id_vehicle = p_idVehicle;
    
    
     EXCEPTION WHEN NO_DATA_FOUND THEN
            raise_application_error(-20022, 'Park or Vehicle not found'); 
END;
/*
BEGIN
--lockVehicleID('S002','FAIL');
--lockVehicleID('FAIL','Teste4');
lockVehicleID('S002','Teste2');
END;
*/
        