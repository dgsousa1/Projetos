using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Drivers;
using DDDSample1.Domain.VehicleDuties;
using System.Linq;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.DriverDuties
{
    public class DriverDutyService : IDriverDutyService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IDriverDutyRepository _repo;
        private readonly IWorkBlockRepository wbRepo;
        private readonly ITripRepository tripRepo;

        private readonly IVehicleDutyRepository vdRepo;
        private readonly IDriverRepository driverRepo;

        public DriverDutyService(){
            
        }

        public DriverDutyService(IUnitOfWork unitOfWork, IDriverDutyRepository repo, IWorkBlockRepository wbRepo, IDriverRepository driverRepo, IVehicleDutyRepository vdRepo, ITripRepository tripRepo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
            this.wbRepo = wbRepo;
            this.driverRepo = driverRepo;
            this.vdRepo = vdRepo;
            this.tripRepo = tripRepo;
        }

        public virtual async Task<List<DriverDutyDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<DriverDutyDto> listDtos = new List<DriverDutyDto>();
            foreach (DriverDuty dd in list)
            {
                listDtos.Add(DriverDutyMap.toDto(dd));
            }
            return listDtos;
        }

        public virtual async Task<DriverDutyDto> GetByIdAsync(DriverDutyId id)
        {
            var dd = await this._repo.GetByIdAsync(id);

            if (dd == null)
                return null;

            return DriverDutyMap.toDto(dd);
        }

        private async Task<bool> checkIfWorkBlocksAreSequential(List<WorkBlockKey> ddWB)
        {
            for (int i = 0; i < ddWB.Count; i++)
            {
                Console.WriteLine("HI");

                if (i != 0)
                {
                    Console.WriteLine("Hey");

                    WorkBlock wb = await wbRepo.GetByKeyAsync(ddWB[i]);
                    Console.WriteLine("wb: " + wb.startInstant);
                    WorkBlock previous = await wbRepo.GetByKeyAsync(ddWB[i - 1]);
                    Console.WriteLine("prev: " + previous.endInstant);

                    if (!System.DateTime.Equals(wb.startInstant, previous.endInstant))
                    {
                        Console.WriteLine("wutface");
                        return false;
                    }
                }

            }
            return true;
        }

        public virtual async Task<DriverDutyDto> AddAsync(DriverDutyDto dto)
        {
            //Verificar se o driver existe na BD
            try
            {
                var Driver = await this.driverRepo.GetDriverByName(dto.driverName);
            }
            catch (Exception)
            {
                throw new BusinessRuleValidationException("Driver doesn't exist!");
            }

            var dd = DriverDutyMap.toDomain(dto);
            //Verificar se os blocos de trabalho são contiguos
            List<WorkBlockKey> ddWb = new List<WorkBlockKey>(dd.workBlocks);
            bool res = await checkIfWorkBlocksAreSequential(ddWb);
            if (!res)
            {
                throw new BusinessRuleValidationException("Work blocks aren't sequential!");
            }

            //Calculo da duração
            int totalDuration = 0;
            foreach (WorkBlockKey k in dd.workBlocks)
            {
                WorkBlock wb = await this.wbRepo.GetByKeyAsync(k);
                totalDuration += wb.workBlockDurationSeconds();
            }

            try
            {
                dd.addDuration(totalDuration);
            }
            catch (BusinessRuleValidationException)
            {
                throw new BusinessRuleValidationException("Work block exceeded max duration");
            }

            /* Verificar se estao no mesmo Vehicle Duty 
            List<WorkBlockKey> wbTras = new List<WorkBlockKey>();
             List<WorkBlockKey> keysToCheck = new List<WorkBlockKey>(dd.workBlocks);
             List<VehicleDuty> vdList = await this.vdRepo.GetAllAsync();

             bool flag = false;
             foreach (VehicleDuty vd in vdList)
             {
                 foreach (WorkBlockKey key in keysToCheck)
                 {
                     if (vd.workBlocks.Contains(key))
                     {
                         flag = true;
                     }
                     else if (flag == true)
                     {
                         wbTras.Add(key);
                         flag = false;
                     }
                 }
             }

             foreach (WorkBlockKey key in wbTras)
             {
                 int thisIndex = keysToCheck.FindIndex(a => a.key.Equals(key.key));
                 WorkBlockKey prev = keysToCheck.ElementAt(thisIndex--);
                 WorkBlock current = await this.wbRepo.GetByKeyAsync(key);
                 WorkBlock previous = await this.wbRepo.GetByKeyAsync(prev);
                 previous.trips.Last<TripNumber>();
             }
             */

            var d = await this._repo.AddAsync(dd);
            await this._unitOfWork.CommitAsync();

            return DriverDutyMap.toDto(d);
        }

        public virtual async Task<DriverDutyDto> UpdateAsync(DriverDutyDto dto)
        {
            var dd = await this._repo.GetByIdAsync(new DriverDutyId(dto.Id));

            if (dd == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return DriverDutyMap.toDto(dd);
        }

        public virtual async Task<DriverDutyDto> InactivateAsync(DriverDutyId id)
        {
            var dd = await this._repo.GetByIdAsync(id);

            if (dd == null)
                return null;

            // change all fields
            dd.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return DriverDutyMap.toDto(dd);

        }

        public virtual async Task<DriverDutyDto> DeleteAsync(DriverDutyId id)
        {
            var dd = await this._repo.GetByIdAsync(id);

            if (dd == null)
                return null;

            if (dd.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active DriverDuty.");

            this._repo.Remove(dd);
            await this._unitOfWork.CommitAsync();

            return DriverDutyMap.toDto(dd);
        }
    }
}