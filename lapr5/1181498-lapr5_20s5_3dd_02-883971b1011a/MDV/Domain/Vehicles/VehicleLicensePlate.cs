using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Vehicles
{
    public class VehicleLicensePlate : ValueObject
    {

        public string licensePlate { get; set; }

        private VehicleLicensePlate()
        {

        }

        public VehicleLicensePlate(string value)
        {
            Regex licensePlateRegex = new Regex(@"^(?:[A-Z]{2}-\d{2}-\d{2})|(?:\d{2}-[A-Z]{2}-\d{2})|(?:\d{2}-\d{2}-[A-Z]{2})$");
            if (licensePlateRegex.IsMatch(value))
            {
                this.licensePlate = value;
            }
            else
                throw new BusinessRuleValidationException("License plate doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return licensePlate;
        }
    }
}