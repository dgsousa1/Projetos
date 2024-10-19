using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System;

namespace DDDSample1.Infrastructure.VehicleDuties
{
    public class VehicleDutyRepository : BaseRepository<VehicleDuty, VehicleDutyId>, IVehicleDutyRepository
    {

        private readonly DbSet<VehicleDuty> _objs;
        public VehicleDutyRepository(DDDSample1DbContext context) : base(context.VehicleDuties)
        {
            this._objs = context.VehicleDuties ?? throw new ArgumentNullException("Vehicle Duty not found");
        }





    }
}