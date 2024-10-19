using System;
using Moq;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;


namespace VehicleDutyControllerTest
{
    public class VehicleDutyControllerTest
    {
        private VehicleDutiesController controller;
        private Mock<VehicleDutyService> serviceMock;

        [Test]
        public void GetAllVehicleDutiesTest()
        {
            this.serviceMock = new Mock<VehicleDutyService>();
            this.controller = new VehicleDutiesController(serviceMock.Object);
            VehicleDutyId aId = new VehicleDutyId(Guid.NewGuid());
            VehicleDutyId bId = new VehicleDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            string[] workBlocksB = { "2" };
            List<VehicleDutyDto> list = new List<VehicleDutyDto>();
            VehicleDutyDto a = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };

            VehicleDutyDto b = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321B",
                workBlocks = workBlocksB,
                duration = 20,
                validDate = "2000/10/11",
                color = "RGB(1,1,3)"
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            Assert.AreEqual(list, controller.GetAll().Result.Value);

        }

        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<VehicleDutyService>();
            this.controller = new VehicleDutiesController(serviceMock.Object);
            VehicleDutyId aId = new VehicleDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            VehicleDutyDto expected = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<VehicleDutyId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            VehicleDutyDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);

        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<VehicleDutyService>();
            this.controller = new VehicleDutiesController(serviceMock.Object);
            VehicleDutyId aId = new VehicleDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            VehicleDutyDto expected = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };
            VehicleDutyDto sent = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };




            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<VehicleDutyDto>())).Returns(Task.FromResult(expected));
            VehicleDutyDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<VehicleDutyService>();
            this.controller = new VehicleDutiesController(serviceMock.Object);
            VehicleDutyId aId = new VehicleDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            VehicleDutyDto expected = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };
            VehicleDutyDto sent = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<VehicleDutyId>())).Returns(Task.FromResult(expected));
            VehicleDutyDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<VehicleDutyService>();
            this.controller = new VehicleDutiesController(serviceMock.Object);
            VehicleDutyId aId = new VehicleDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            VehicleDutyDto expected = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };
            VehicleDutyDto sent = new VehicleDutyDto
            {
                Id = aId.AsString(),
                code = "122321A",
                workBlocks = workBlocksA,
                duration = 10,
                validDate = "2000/10/10",
                color = "RGB(1,1,1)"
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<VehicleDutyId>())).Returns(Task.FromResult(expected));
            VehicleDutyDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
