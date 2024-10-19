using System;


namespace DDDSample1.Domain.Drivers
{
    public class DriverMap
    {
        public static DriverDto toDto(Driver driver)
        {
            return new DriverDto
            {
                Id = driver.Id.AsString(),
                mecNumber = driver.mecNumber.mecNumber,
                name = driver.name,
                birthDate = driver.birthDate.ToString(),
                cc = driver.cc.cc,
                nif = driver.nif.nif,
                driverTypes = driver.driverTypes,
                startDateCompany = driver.startDateCompany.ToString(),
                finalDateCompany = driver.finalDateCompany.ToString(),
                licenseNumber = driver.driverLicense.licenseNumber,
                licenseDate = driver.driverLicense.emissionDate.ToString()
            };
        }

        public static Driver toDomain(DriverDto dto)
        {
            return new Driver(dto.mecNumber, dto.name,
                       dto.birthDate, dto.cc, dto.nif, dto.driverTypes,
                       dto.startDateCompany, dto.finalDateCompany, dto.licenseNumber, dto.licenseDate);
        }

    }
}