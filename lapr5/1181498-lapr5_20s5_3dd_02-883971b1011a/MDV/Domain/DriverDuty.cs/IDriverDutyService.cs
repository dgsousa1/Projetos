using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Drivers;
using DDDSample1.Domain.VehicleDuties;
using System.Linq;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.DriverDuties
{
    public interface IDriverDutyService
    {
        Task<List<DriverDutyDto>> GetAllAsync();
        Task<DriverDutyDto> GetByIdAsync(DriverDutyId id);
        Task<DriverDutyDto> AddAsync(DriverDutyDto dto);
        Task<DriverDutyDto> UpdateAsync(DriverDutyDto dto);
        Task<DriverDutyDto> InactivateAsync(DriverDutyId id);
        Task<DriverDutyDto> DeleteAsync(DriverDutyId id);
    }
}