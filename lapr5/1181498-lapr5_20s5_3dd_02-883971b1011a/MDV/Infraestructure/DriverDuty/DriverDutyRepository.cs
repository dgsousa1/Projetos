using DDDSample1.Domain.DriverDuties;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System;

namespace DDDSample1.Infrastructure.DriverDuties
{
    public class DriverDutyRepository : BaseRepository<DriverDuty, DriverDutyId>, IDriverDutyRepository
    {


        public DriverDutyRepository(DDDSample1DbContext context) : base(context.DriverDuties)
        {
        
        }

        

    }
}