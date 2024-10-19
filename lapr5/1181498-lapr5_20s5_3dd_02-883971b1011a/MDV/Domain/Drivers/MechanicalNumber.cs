using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Drivers
{
    public class MechanicalNumber : ValueObject
    {

        public string mecNumber { get; set; }

        private MechanicalNumber()
        {

        }

        public MechanicalNumber(string value)
        {
            if (value.Length == 9)
                this.mecNumber = value;
            else
                throw new BusinessRuleValidationException("Mecanografic number doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return mecNumber;
        }
    }
}