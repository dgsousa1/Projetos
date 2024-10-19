using NUnit.Framework;
using System;
using DDDSample1.Domain.Drivers;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class DriverTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateDriverSuccess()
        {
            Driver d1 = new Driver("125436789", "Driver1", "2000/10/10", "88878976", 123452389,
            "Autocarro", "2020/12/12", "2020/12/20", "123456789012", "2010/12/20");

            Assert.AreEqual("125436789", d1.mecNumber.mecNumber);
        }

        [Test]
        public void CreateDriverFailAge()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Driver("125436789", "Driver1", "2010/10/10", "88878976", 123452389,
            "Autocarro", "2020/12/12", "2020/12/20", "123456789012", "2010/12/20"));
        }

        [Test]
        public void CreateDriverFailMecNumber()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Driver("12543678", "DriverFail", "2000/10/10", "88878976", 123452389,
           "Autocarro", "2020/12/12", "2020/12/20", "123456789012", "2010/12/20"));
        }

        [Test]
        public void CreateDriverFailCC()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Driver("125436788", "DriverFail", "2000/10/10", "8887897", 123452389,
           "Autocarro", "2020/12/12", "2020/12/20", "123456789012", "2010/12/20"));
        }

        [Test]
        public void CreateDriverFailNIF()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Driver("125436788", "DriverFail", "2000/10/10", "88878976", 12345238,
           "Autocarro", "2020/12/12", "2020/12/20", "123456789012", "2010/12/20"));
        }

        [Test]
        public void CreateDriverFailLicenseNumber()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Driver("125436788", "DriverFail", "2000/10/10", "88878976", 123452389,
           "Autocarro", "2020/12/12", "2020/12/20", "12345678901", "2010/12/20"));
        }

    }
}