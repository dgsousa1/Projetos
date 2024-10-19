using DDDSample1.Domain.Shared;
using System;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;
using DDDSample1.Domain.Drivers;

namespace DDDSample1.Domain.Users
{
    public class User : Entity<UserId>, IAggregateRoot
    {
        public string name { get; private set; }

        public string role { get; private set; }

        public string username { get; private set; }

        public Password password { get; private set; }

        public Telephone telephone { get; private set; }

        public Email email { get; private set; }

        public NIF nif { get; private set; }

        public bool Active { get; private set; }

        private User()
        {
            this.Active = true;
        }

        public User(string name, string role, string username, string password, int telephone, string email, int nif)
        {
            this.Id = new UserId(Guid.NewGuid());

            if (name.Length <= 75)
                this.name = name;
            else
                throw new BusinessRuleValidationException("Name too long!");

            if (username.Length >= 3)
                this.username = username;
            else
                throw new BusinessRuleValidationException("Username needs to have atleast 3 characters");

            this.password = new Password(password);
            this.telephone = new Telephone(telephone);
            this.email = new Email(email);
            this.nif = new NIF(nif);
            this.role = role;

        }

        public void ChangePassword(string password)
        {
            if (password.Length >= 6)
            {
                if (!this.Active)
                {
                    throw new BusinessRuleValidationException("It is not possible to change the password to an inactive User.");
                }
                else
                {
                    this.password = new Password(password);
                }
            }
            else
                throw new BusinessRuleValidationException("Password needs to have atleast 6 characters");
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }
    }
}