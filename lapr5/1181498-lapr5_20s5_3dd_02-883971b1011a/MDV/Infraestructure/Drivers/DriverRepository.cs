using DDDSample1.Domain.Drivers;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System;

namespace DDDSample1.Infrastructure.Drivers
{
    public class DriverRepository : BaseRepository<Driver, DriverId>, IDriverRepository
    {
        private readonly DbSet<Driver> _objs;

        public DriverRepository(DDDSample1DbContext context) : base(context.Drivers)
        {
            this._objs = context.Drivers ?? throw new ArgumentNullException("Drivers not found");
        }

        public async Task<Driver> GetDriverByName(string name){
            return await this._objs.Where(x => name.Equals(x.name)).FirstOrDefaultAsync();
        }

    }
}