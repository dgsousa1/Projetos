using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace DDDSample1.Domain.Users
{
    public class Email : ValueObject
    {

        public string email { get; set; }

        private Email()
        {

        }

        public Email(string value)
        {
            Regex emailRegex = new Regex(@"^(?:[0-9a-zA-Z]*@[a-zA-Z]*.[a-zA-Z]*)$");
            if (emailRegex.IsMatch(value))
            {
                this.email = value;
            }
            else
                throw new BusinessRuleValidationException("Email doesn't match the right format");
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return email;
        }
    }
}