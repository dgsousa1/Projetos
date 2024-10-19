using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Users
{
    public class Password : ValueObject
    {

        public string password { get; set; }

        private Password()
        {

        }

        public Password(string value)
        {
            if (value.Length >=  6)
                this.password = value;
            else
                throw new BusinessRuleValidationException("Password too short!");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return password;
        }
    }
}