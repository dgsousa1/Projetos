using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;
using System;
using System.Linq;

namespace DDDSample1.Domain.WorkBlocks
{
    public interface IWorkBlockService
    {
        Task<List<WorkBlockDto>> GetAllAsync();
        Task<List<WorkBlockInSecondsDto>> GetAllInSecondsAsync();
        Task<WorkBlockDto> GetByIdAsync(WorkBlockId id);
        Task<WorkBlockDto> AddAsync(WorkBlockDto dto);
        Task<WorkBlockDto> UpdateAsync(WorkBlockDto dto);
        Task<WorkBlockDto> InactivateAsync(WorkBlockId id);
        Task<WorkBlockDto> DeleteAsync(WorkBlockId id);
    }
}