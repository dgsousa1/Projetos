using DDDSample1.Domain.Shared;
using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Domain.VehicleDuties
{
    public class VehicleDuty : Entity<VehicleDutyId>
    {

        public string code { get; private set; }

        public string name { get; private set; }

        public ICollection<WorkBlockKey> workBlocks { get; private set; }

        public int duration { get; private set; }

        public DateTime validDate { get; private set; }

        public string color { get; private set; }

        public bool Active { get; private set; }

        private VehicleDuty()
        {
            this.Active = true;
        }

        public VehicleDuty(string code, string name, List<WorkBlockKey> workBlocks, string validDate, string color)
        {
            this.Id = new VehicleDutyId(Guid.NewGuid());
            if (code.Length <= 10)
                this.code = code;
            else
                throw new BusinessRuleValidationException("Invalid code");

            if (name.Length <= 50)
                this.name = name;
            else
                throw new BusinessRuleValidationException("Invalid name");

            this.workBlocks = workBlocks;
            this.validDate = Convert.ToDateTime(validDate);
            this.color = color;
        }

        public void addDuration(int duration)
        {
            if (duration > Settings.MAX_DURATION_IN_SECONDS_VEHICLE_DUTY)
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