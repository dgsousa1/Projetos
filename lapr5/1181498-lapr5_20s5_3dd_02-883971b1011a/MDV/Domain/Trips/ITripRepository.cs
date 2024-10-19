using DDDSample1.Domain.Shared;
using System.Threading.Tasks;


namespace DDDSample1.Domain.Trips
{
    public interface ITripRepository : IRepository<Trip, TripId>
    {
        Task<Trip> GetByTripNumber(int number);
    }
}