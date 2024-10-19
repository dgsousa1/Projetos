using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class LineName : ValueObject
    {

        public string lineName { get; set; }

        private LineName()
        {

        }

        public LineName(string value)
        {
            if (value.Length <= 25)
            {
                this.lineName = value;
            }
            else
                throw new BusinessRuleValidationException("Line Name doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return lineName;
        }
    }
}