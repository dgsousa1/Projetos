using DDDSample1.Domain.Vehicles;
using DDDSample1.Infrastructure.Shared;

namespace DDDSample1.Infrastructure.Vehicles
{
    public class VehicleRepository : BaseRepository<Vehicle, VehicleId>, IVehicleRepository
    {
      
        public VehicleRepository(DDDSample1DbContext context):base(context.Vehicles)
        {
            
        }

    }
}