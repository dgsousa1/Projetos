using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Drivers
{
    public class NIF : ValueObject
    {

        public int nif { get; set; }

        private NIF()
        {

        }

        public NIF(int value)
        {
            if (value.ToString().Length == 9)
                this.nif = value;
            else
                throw new BusinessRuleValidationException("NIF doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return nif;
        }
    }
}