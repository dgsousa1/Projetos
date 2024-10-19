using NUnit.Framework;
using System;
using DDDSample1.Domain.Users;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class UserTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateUserSuccess()
        {
            User u1 = new User("Ru232", "administrador", "ninja3338", "123444", 123436789, "emaiel@gmail.com", 123454289);
            Assert.AreEqual("Ru232", u1.name);
        }

        [Test]
        public void CreateUserFailUsernameTooShort()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new User("Ru232", "administrador", "nn", "123444", 123436789, "emaiel@gmail.com", 123454289));
        }

        [Test]
        public void CreateUserFailInvalidEmail()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new User("Ru232", "administrador", "ninja3338", "123444", 123436789, "emaigmail.com", 123454289));
        }

        [Test]
        public void CreateUserFailInvalidNIF()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new User("Ru232", "administrador", "ninja3338", "123444", 123436789, "emaiel@gmail.com", 12389));
        }

        [Test]
        public void CreateUserFailInvalidTelephone()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new User("Ru232", "administrador", "ninja3338", "123444", 1234, "emaiel@gmail.com", 123454289));
        }

        [Test]
        public void CreateUserFailNameTooLong()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new User("Ru232tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt", "administrador", "ninja3338", "123444", 123436789, "emaiel@gmail.com", 123454289));
        }

    }
}