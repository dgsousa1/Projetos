using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;

namespace DDDSample1.Domain.VehicleDuties
{
    public class VehicleDutyService : IVehicleDutyService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IVehicleDutyRepository _repo;
        private readonly IWorkBlockRepository wbRepo;

        public VehicleDutyService()
        {

        }
        public VehicleDutyService(IUnitOfWork unitOfWork, IVehicleDutyRepository repo, IWorkBlockRepository wbRepo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
            this.wbRepo = wbRepo;
        }

        public virtual async Task<List<VehicleDutyDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<VehicleDutyDto> listDtos = new List<VehicleDutyDto>();
            foreach (VehicleDuty vd in list)
            {
                listDtos.Add(VehicleDutyMap.toDto(vd));
            }
            return listDtos;
        }

        public virtual async Task<VehicleDutyDto> GetByIdAsync(VehicleDutyId id)
        {
            var vd = await this._repo.GetByIdAsync(id);

            if (vd == null)
                return null;

            return VehicleDutyMap.toDto(vd);
        }

        public virtual async Task<VehicleDutyDto> AddAsync(VehicleDutyDto dto)
        {

            var vd = VehicleDutyMap.toDomain(dto);

            int totalDuration = 0;
            foreach (WorkBlockKey k in vd.workBlocks)
            {
                WorkBlock wb = await wbRepo.GetByKeyAsync(k);
                totalDuration += wb.workBlockDurationSeconds();
            }

            try
            {
                vd.addDuration(totalDuration);
            }
            catch (BusinessRuleValidationException)
            {

                throw new BusinessRuleValidationException("Work block exceeded max duration");
            }


            await this._repo.AddAsync(vd);

            await this._unitOfWork.CommitAsync();

            return VehicleDutyMap.toDto(vd);
        }

        public virtual async Task<VehicleDutyDto> UpdateAsync(VehicleDutyDto dto)
        {
            var vd = await this._repo.GetByIdAsync(new VehicleDutyId(dto.Id));

            if (vd == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return VehicleDutyMap.toDto(vd);
        }

        public virtual async Task<VehicleDutyDto> InactivateAsync(VehicleDutyId id)
        {
            var vd = await this._repo.GetByIdAsync(id);

            if (vd == null)
                return null;

            // change all fields
            vd.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return VehicleDutyMap.toDto(vd);

        }

        public virtual async Task<VehicleDutyDto> DeleteAsync(VehicleDutyId id)
        {
            var vd = await this._repo.GetByIdAsync(id);

            if (vd == null)
                return null;

            if (vd.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active VehicleDuty.");

            this._repo.Remove(vd);
            await this._unitOfWork.CommitAsync();

            return VehicleDutyMap.toDto(vd);
        }
    }
}