using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class Orientation : ValueObject
    {

        public string orientation { get; set; }

        private Orientation(){

        }

        public Orientation(string value)
        {
            Regex ccRegex = new Regex("Go|go|Return|return");
            if (ccRegex.IsMatch(value))
            {
                this.orientation = value;
            }
            else
                throw new BusinessRuleValidationException("Orientation doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents(){
            yield return orientation;
        }
    }
}