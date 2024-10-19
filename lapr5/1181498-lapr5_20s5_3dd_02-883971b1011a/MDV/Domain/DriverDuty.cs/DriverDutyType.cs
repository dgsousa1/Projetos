using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.DriverDuties
{
    public class DriverDutyType : ValueObject
    {

        public string type { get; set; }

        private DriverDutyType()
        {

        }

        public DriverDutyType(string value)
        {
            if (value.Length <= 50)
            {
                this.type=value;
            }
            else
            {
                throw new BusinessRuleValidationException("Driver Duty Type is too big!");
            }
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return type;
        }
    }
}