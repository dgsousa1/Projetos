using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Vehicles
{
    public interface IVehicleService
    {
        Task<List<VehicleDto>> GetAllAsync();
        Task<VehicleDto> GetByIdAsync(VehicleId id);
        Task<VehicleDto> AddAsync(VehicleDto dto);
        Task<VehicleDto> UpdateAsync(VehicleDto dto);
        Task<VehicleDto> InactivateAsync(VehicleId id);
        Task<VehicleDto> DeleteAsync(VehicleId id);
    }
}