using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using System;

namespace DDDSample1.Domain.Drivers
{
    public interface IDriverService
    {
        Task<List<DriverDto>> GetAllAsync();
        Task<DriverDto> GetByIdAsync(DriverId id);
        Task<DriverDto> AddAsync(DriverDto dto);
        Task<DriverDto> UpdateAsync(DriverDto dto);
        Task<DriverDto> InactivateAsync(DriverId id);
        Task<DriverDto> DeleteAsync(DriverId id);
    }
}