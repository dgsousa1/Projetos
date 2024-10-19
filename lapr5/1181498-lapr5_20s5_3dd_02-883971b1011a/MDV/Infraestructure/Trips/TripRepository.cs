using DDDSample1.Domain.Trips;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System;

namespace DDDSample1.Infrastructure.Trips
{
    public class TripRepository : BaseRepository<Trip, TripId>, ITripRepository
    {
        private readonly DbSet<Trip> _objs;
        public TripRepository(DDDSample1DbContext context) : base(context.Trips)
        {
            this._objs = context.Trips ?? throw new ArgumentNullException("Trips not found");

        }

        public async Task<Trip> GetByTripNumber(int number)
        {
            return await this._objs.Where(x => number==x.tripNumber.key).FirstOrDefaultAsync();

        }

    }
}