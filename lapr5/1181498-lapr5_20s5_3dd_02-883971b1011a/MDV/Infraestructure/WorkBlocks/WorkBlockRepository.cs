using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System;


namespace DDDSample1.Infrastructure.WorkBlocks
{
    public class WorkBlockRepository : BaseRepository<WorkBlock, WorkBlockId>, IWorkBlockRepository
    {
        private readonly DbSet<WorkBlock> _objs;

        public WorkBlockRepository(DDDSample1DbContext context) : base(context.WorkBlocks)
        {
            this._objs = context.WorkBlocks ?? throw new ArgumentNullException("Work blocks not found");
        }

        public async Task<WorkBlock> GetByKeyAsync(WorkBlockKey key)
        {
            return await this._objs.Where(x => key.Equals(x.key)).FirstOrDefaultAsync();
        }

    }
}