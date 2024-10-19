create or replace FUNCTION GETUNLOCKEDVEHICLES RETURN SYS_REFCURSOR
IS
    output SYS_REFCURSOR;
BEGIN
        OPEN output FOR
        SELECT TRIP.id_user ,TRIP.id_vehicle 
        FROM TRIP INNER JOIN VEHICLES ON TRIP.id_vehicle = VEHICLES.id_vehicle
                           INNER JOIN LOCATIONS ON LOCATIONS.id_location = Vehicles.id_location
        WHERE LOCATIONS.latitude=0 and LOCATIONS.longitude=0;
        
    return(output);
END;