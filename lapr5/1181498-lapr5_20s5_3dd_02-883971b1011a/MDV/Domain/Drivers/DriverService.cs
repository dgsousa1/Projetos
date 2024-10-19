using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using System;

namespace DDDSample1.Domain.Drivers
{
    public class DriverService : IDriverService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IDriverRepository _repo;

        public DriverService(){
            
        }
        public DriverService(IUnitOfWork unitOfWork, IDriverRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public virtual async Task<List<DriverDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<DriverDto> listDto = new List<DriverDto>();
            foreach (Driver d in list)
            {
                listDto.Add(DriverMap.toDto(d));
            }
            return listDto;
        }

        public virtual async Task<DriverDto> GetByIdAsync(DriverId id)
        {
            var Driver = await this._repo.GetByIdAsync(id);

            if (Driver == null)
                return null;

            return DriverMap.toDto(Driver);
        }

        public virtual async Task<DriverDto> AddAsync(DriverDto dto)
        {
            var Driver = DriverMap.toDomain(dto);

            await this._repo.AddAsync(Driver);

            await this._unitOfWork.CommitAsync();

            return DriverMap.toDto(Driver);
        }

        public virtual async Task<DriverDto> UpdateAsync(DriverDto dto)
        {
            var Driver = await this._repo.GetByIdAsync(new DriverId(dto.Id));

            if (Driver == null)
                return null;

            // change all field
            Driver.ChangeName(dto.name);

            await this._unitOfWork.CommitAsync();

            return DriverMap.toDto(Driver);

        }

        public virtual async Task<DriverDto> InactivateAsync(DriverId id)
        {
            var Driver = await this._repo.GetByIdAsync(id);

            if (Driver == null)
                return null;

            // change all fields
            Driver.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return DriverMap.toDto(Driver);

        }

        public virtual async Task<DriverDto> DeleteAsync(DriverId id)
        {
            var Driver = await this._repo.GetByIdAsync(id);

            if (Driver == null)
                return null;

            if (Driver.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active Driver.");

            this._repo.Remove(Driver);
            await this._unitOfWork.CommitAsync();

            return DriverMap.toDto(Driver);

        }
    }
}