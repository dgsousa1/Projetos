using NUnit.Framework;
using DDDSample1.Controllers;
using Moq;
using DDDSample1.Domain.Drivers;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;


namespace Controllers.Test
{

    public class DriverControllerTest
    {

        [SetUp]
        public void Setup()
        {

        }

        [Test]
        public void GetAllDriversTest()
        {
            Mock<DriverService> serviceMock = new Mock<DriverService>();
            DriversController controller = new DriversController(serviceMock.Object);
            DriverId aId = new DriverId(Guid.NewGuid());
            DriverId bId = new DriverId(Guid.NewGuid());
            List<DriverDto> list = new List<DriverDto>();

            DriverDto a = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };
            DriverDto b = new DriverDto
            {
                Id = bId.AsString(),
                mecNumber = "335436789",
                name = "DriverB",
                birthDate = "2000/10/10",
                cc = "88878988",
                nif = 123882389,
                driverTypes = "Metro",
                startDateCompany = "2020/12/13",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456786512",
                licenseDate = "2010/12/20"
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            List<DriverDto> result = new List<DriverDto>(controller.GetAll().Result.Value);
            Assert.AreEqual(list, result);

        }

        [Test]
        public void GetByIdTest()
        {
            Mock<DriverService> serviceMock = new Mock<DriverService>();
            DriversController controller = new DriversController(serviceMock.Object);
            DriverId aId = new DriverId(Guid.NewGuid());
            DriverDto a = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };
            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<DriverId>())).Returns(Task.FromResult(a));
            Assert.AreEqual(a, controller.GetGetById(aId.AsString()).Result.Value);
        }


        [Test]
        public void UpdateTest()
        {
            Mock<DriverService> serviceMock = new Mock<DriverService>();
            DriversController controller = new DriversController(serviceMock.Object);
            DriverId aId = new DriverId(Guid.NewGuid());
            DriverDto a = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };

            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<DriverDto>())).Returns(Task.FromResult(a));
            var result = controller.Update(aId.AsString(), a).Result.Value;

        }

        [Test]
        public void SoftDeleteTest()
        {
            Mock<DriverService> serviceMock = new Mock<DriverService>();
            DriversController controller = new DriversController(serviceMock.Object);
            DriverId aId = new DriverId(Guid.NewGuid());
            DriverDto expected = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };
            DriverDto sent = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<DriverId>())).Returns(Task.FromResult(expected));
            DriverDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void HardDeleteTest()
        {
            Mock<DriverService> serviceMock = new Mock<DriverService>();
            DriversController controller = new DriversController(serviceMock.Object);
            DriverId aId = new DriverId(Guid.NewGuid());
            DriverDto expected = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };
            DriverDto sent = new DriverDto
            {
                Id = aId.AsString(),
                mecNumber = "125436789",
                name = "DriverA",
                birthDate = "2000/10/10",
                cc = "88878976",
                nif = 123452389,
                driverTypes = "Autocarro",
                startDateCompany = "2020/12/12",
                finalDateCompany = "2020/12/20",
                licenseNumber = "123456789012",
                licenseDate = "2010/12/20"
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<DriverId>())).Returns(Task.FromResult(expected));
            DriverDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
