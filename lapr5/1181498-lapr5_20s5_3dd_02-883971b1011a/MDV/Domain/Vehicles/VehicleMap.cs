using System;


namespace DDDSample1.Domain.Vehicles
{
    public class VehicleMap
    {
        public static VehicleDto toDto(Vehicle vehicle)
        {
            return new VehicleDto
            {
                Id = vehicle.Id.AsString(),
                licensePlate = vehicle.licensePlate.licensePlate,
                VIN = vehicle.VIN.vin,
                type = vehicle.type,
                startDateService = vehicle.startDateService.ToString()
            };
        }

        public static Vehicle toDomain(VehicleDto dto)
        {
            return new Vehicle(dto.licensePlate,
                        dto.VIN, dto.type, dto.startDateService);
        }

    }
}