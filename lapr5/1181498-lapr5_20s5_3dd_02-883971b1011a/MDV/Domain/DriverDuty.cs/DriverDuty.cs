using DDDSample1.Domain.Shared;
using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Domain.DriverDuties
{
    public class DriverDuty : Entity<DriverDutyId>
    {

        public string mecNumber { get; private set; }
        public string driverName { get; private set; }
        public string color { get; private set; }
        public DriverDutyType type { get; private set; }
        public ICollection<WorkBlockKey> workBlocks { get; private set; }
        public int duration { get; private set; }
        public DateTime validDate { get; private set; }
        public bool Active { get; private set; }

        private DriverDuty()
        {
            this.Active = true;
        }

        public DriverDuty(string mecNumber, string driverName, string color, string type, List<WorkBlockKey> workBlocks, string validDate)
        {
            this.Id = new DriverDutyId(Guid.NewGuid());
            if (mecNumber.Length <= 10)
            {
                this.mecNumber = mecNumber;
            }
            else
            {
                throw new BusinessRuleValidationException("Mechanical Number is too big!");
            }
            if (driverName.Length <= 100)
            {
                this.driverName = driverName;
            }
            else
            {
                throw new BusinessRuleValidationException("Driver Name is too big!");
            }
            this.workBlocks = workBlocks;
            this.validDate = Convert.ToDateTime(validDate);
            this.color = color;
            this.type = new DriverDutyType(type);
        }

        public void addDuration(int duration)
        {
            if (duration > Settings.MAX_DURATION_IN_SECONDS_DRIVER_DUTY)
            {

                throw new BusinessRuleValidationException("Duration has exceeded the maximum value");
            }
            else
            {

                this.duration = duration;
            }
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }
    }
}