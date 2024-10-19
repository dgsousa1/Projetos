using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class VehicleTypeName : ValueObject
    {

        public string vehicleType { get; set; }

        private VehicleTypeName()
        {

        }

        public VehicleTypeName(string value)
        {
            if (value.Length <= 50)
            {
                this.vehicleType = value;
            }
            else
                throw new BusinessRuleValidationException("Vehicle Type doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return vehicleType;
        }
    }
}