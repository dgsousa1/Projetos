using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Vehicles
{
    public class VehicleService : IVehicleService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IVehicleRepository _repo;

        public VehicleService()
        {

        }

        public VehicleService(IUnitOfWork unitOfWork, IVehicleRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public virtual async Task<List<VehicleDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();
            List<VehicleDto> listDto = new List<VehicleDto>();
            foreach (Vehicle v in list)
            {
                listDto.Add(VehicleMap.toDto(v));
            }

            return listDto;
        }

        public virtual async Task<VehicleDto> GetByIdAsync(VehicleId id)
        {
            var Vehicle = await this._repo.GetByIdAsync(id);

            if (Vehicle == null)
                return null;

            return VehicleMap.toDto(Vehicle);

        }

        public virtual async Task<VehicleDto> AddAsync(VehicleDto dto)
        {
            var Vehicle = VehicleMap.toDomain(dto);

            await this._repo.AddAsync(Vehicle);

            await this._unitOfWork.CommitAsync();

            return VehicleMap.toDto(Vehicle);
        }

        public virtual async Task<VehicleDto> UpdateAsync(VehicleDto dto)
        {
            var Vehicle = await this._repo.GetByIdAsync(new VehicleId(dto.Id));

            if (Vehicle == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return VehicleMap.toDto(Vehicle);

        }

        public virtual async Task<VehicleDto> InactivateAsync(VehicleId id)
        {
            var Vehicle = await this._repo.GetByIdAsync(id);

            if (Vehicle == null)
                return null;

            // change all fields
            Vehicle.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return VehicleMap.toDto(Vehicle);

        }

        public virtual async Task<VehicleDto> DeleteAsync(VehicleId id)
        {
            var Vehicle = await this._repo.GetByIdAsync(id);

            if (Vehicle == null)
                return null;

            if (Vehicle.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active Vehicle.");

            this._repo.Remove(Vehicle);
            await this._unitOfWork.CommitAsync();

            return VehicleMap.toDto(Vehicle);

        }
    }
}