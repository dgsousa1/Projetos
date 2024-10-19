using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Drivers
{
    public class DriverCC : ValueObject
    {

        public string cc { get; set; }

        private DriverCC(){

        }

        public DriverCC(string value)
        {
            Regex ccRegex = new Regex("^[0-9]{8}$");
            if (ccRegex.IsMatch(value))
            {
                this.cc = value;
            }
            else
                throw new BusinessRuleValidationException("CC number doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents(){
            yield return cc;
        }
    }
}