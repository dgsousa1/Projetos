using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Vehicles
{
    public class VehicleVIN : ValueObject
    {

        public string vin { get; set; }

        private VehicleVIN()
        {

        }

        public VehicleVIN(string value)
        {
            if (value.Length == 17)
                this.vin = value;
            else
                throw new BusinessRuleValidationException("Vehicle Identification Number doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return vin;
        }
    }
}