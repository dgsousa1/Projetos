using DDDSample1.Domain.Shared;
using System.Threading.Tasks;



namespace DDDSample1.Domain.WorkBlocks
{
    public interface IWorkBlockRepository : IRepository<WorkBlock, WorkBlockId>
    {
        public Task<WorkBlock> GetByKeyAsync(WorkBlockKey key);

    }
}