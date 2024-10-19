using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Users
{
    public class Telephone : ValueObject
    {

        public int telephone { get; set; }

        private Telephone()
        {

        }

        public Telephone(int value)
        {
            if (value.ToString().Length == 9)
                this.telephone = value;
            else
                throw new BusinessRuleValidationException("Telephone doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return telephone;
        }
    }
}