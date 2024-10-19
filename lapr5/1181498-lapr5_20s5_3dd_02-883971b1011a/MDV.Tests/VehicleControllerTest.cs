using System;
using Moq;
using DDDSample1.Domain.Vehicles;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;


namespace VehicleControllerTest
{
    public class VehicleControllerTest
    {
        private VehiclesController controller;
        private Mock<VehicleService> serviceMock;
        [Test]
        public void GetAllVehiclesTest()
        {
            this.serviceMock = new Mock<VehicleService>();
            this.controller = new VehiclesController(serviceMock.Object);
            VehicleId aId = new VehicleId(Guid.NewGuid());
            VehicleId bId = new VehicleId(Guid.NewGuid());
            List<VehicleDto> list = new List<VehicleDto>();
            VehicleDto a = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };

            VehicleDto b = new VehicleDto
            {
                Id = bId.AsString(),
                licensePlate = "AB-33-22",
                VIN = "12345679811177771",
                type = "Metro",
                startDateService = "2020/12/13"
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            Assert.AreEqual(list, controller.GetAll().Result.Value);
        }


        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<VehicleService>();
            this.controller = new VehiclesController(serviceMock.Object);
            VehicleId aId = new VehicleId(Guid.NewGuid());
            VehicleDto expected = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<VehicleId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            VehicleDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);

        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<VehicleService>();
            this.controller = new VehiclesController(serviceMock.Object);
            VehicleId aId = new VehicleId(Guid.NewGuid());
            VehicleDto expected = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };

            VehicleDto sent = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };


            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<VehicleDto>())).Returns(Task.FromResult(expected));
            VehicleDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<VehicleService>();
            this.controller = new VehiclesController(serviceMock.Object);
            VehicleId aId = new VehicleId(Guid.NewGuid());
            VehicleDto expected = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };

            VehicleDto sent = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };


            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<VehicleId>())).Returns(Task.FromResult(expected));
            VehicleDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }


        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<VehicleService>();
            this.controller = new VehiclesController(serviceMock.Object);
            VehicleId aId = new VehicleId(Guid.NewGuid());
            VehicleDto expected = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };

            VehicleDto sent = new VehicleDto
            {
                Id = aId.AsString(),
                licensePlate = "AB-00-00",
                VIN = "12345679811111111",
                type = "Autocarro",
                startDateService = "2020/12/12"
            };


            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<VehicleId>())).Returns(Task.FromResult(expected));
            VehicleDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
