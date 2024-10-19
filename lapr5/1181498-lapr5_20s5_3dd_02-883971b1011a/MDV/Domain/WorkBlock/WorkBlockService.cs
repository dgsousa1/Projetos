using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;
using System;
using System.Linq;

namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockService : IWorkBlockService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IWorkBlockRepository _repo;

        public WorkBlockService(){

        }

        public WorkBlockService(IUnitOfWork unitOfWork, IWorkBlockRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public virtual async Task<List<WorkBlockDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<WorkBlockDto> listDto = new List<WorkBlockDto>();

            foreach (var wb in list)
            {
                listDto.Add(WorkBlockMap.toDto(wb));
            }
            return listDto;
        }

        public virtual async Task<List<WorkBlockInSecondsDto>> GetAllInSecondsAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<WorkBlockInSecondsDto> listDto = new List<WorkBlockInSecondsDto>();

            foreach (var wb in list)
            {
                listDto.Add(WorkBlockMap.toDtoInSeconds(wb));
            }
            return listDto;
        }


        public virtual async Task<WorkBlockDto> GetByIdAsync(WorkBlockId id)
        {
            var wb = await this._repo.GetByIdAsync(id);

            if (wb == null)
            {
                return null;
            }

            return WorkBlockMap.toDto(wb);

        }

        public virtual async Task<WorkBlockDto> AddAsync(WorkBlockDto dto)
        {

            var WorkBlock = WorkBlockMap.toDomain(dto);

            await this._repo.AddAsync(WorkBlock);

            await this._unitOfWork.CommitAsync();

            return WorkBlockMap.toDto(WorkBlock);
        }

        public virtual async Task<WorkBlockDto> UpdateAsync(WorkBlockDto dto)
        {
            var wb = await this._repo.GetByIdAsync(new WorkBlockId(dto.Id));

            if (wb == null)
                return null;

            // change all field
            //WorkBlock.ChangeName(dto.name);

            await this._unitOfWork.CommitAsync();

            return WorkBlockMap.toDto(wb);

        }

        public virtual async Task<WorkBlockDto> InactivateAsync(WorkBlockId id)
        {
            var wb = await this._repo.GetByIdAsync(id);

            if (wb == null)
                return null;

            // change all fields
            wb.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return WorkBlockMap.toDto(wb);

        }

        public virtual async Task<WorkBlockDto> DeleteAsync(WorkBlockId id)
        {
            var wb = await this._repo.GetByIdAsync(id);

            if (wb == null)
                return null;

            if (wb.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active WorkBlock.");

            this._repo.Remove(wb);
            await this._unitOfWork.CommitAsync();

            return WorkBlockMap.toDto(wb);

        }
    }
}