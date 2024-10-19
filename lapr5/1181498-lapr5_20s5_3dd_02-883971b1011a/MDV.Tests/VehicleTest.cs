using NUnit.Framework;
using System;
using DDDSample1.Domain.Vehicles;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class VehicleTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateVehicleSuccess()
        {
            Vehicle v1 = new Vehicle("AB-00-00", "12345679811111111", "Autocarro", "2020/12/12");

            Assert.AreEqual("AB-00-00", v1.licensePlate.licensePlate);
        }

        [Test]
        public void CreateVehicleFailLicenseNumber()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Vehicle("AB-00-0", "12345679811111111", "Autocarro", "2020/12/12"));
        }

        [Test]
        public void CreateVehicleFailVin()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Vehicle("AB-00-00", "123456798111", "Autocarro", "2020/12/12"));
        }


    }
}