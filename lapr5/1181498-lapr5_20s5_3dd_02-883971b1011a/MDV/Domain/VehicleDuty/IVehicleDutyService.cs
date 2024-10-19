using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;

namespace DDDSample1.Domain.VehicleDuties
{
    public interface IVehicleDutyService
    {
        Task<List<VehicleDutyDto>> GetAllAsync();
        Task<VehicleDutyDto> GetByIdAsync(VehicleDutyId id);
        Task<VehicleDutyDto> AddAsync(VehicleDutyDto dto);
        Task<VehicleDutyDto> UpdateAsync(VehicleDutyDto dto);
        Task<VehicleDutyDto> InactivateAsync(VehicleDutyId id);
        Task<VehicleDutyDto> DeleteAsync(VehicleDutyId id);
    }
}