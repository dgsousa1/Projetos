using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Drivers
{
    public class DriverLicense : ValueObject
    {

        public string licenseNumber { get; set; }

        public DateTime emissionDate { get; private set; }

        private DriverLicense()
        {

        }

        public DriverLicense(string number, string date)
        {
            if (number.Length == 12)
                this.licenseNumber = number;
            else
            {
                throw new BusinessRuleValidationException("license number doesn't match the right format");
            }
            this.emissionDate = Convert.ToDateTime(date);
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return licenseNumber;
            yield return emissionDate;
        }
    }
}