using System;
using Moq;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;


namespace DriverDutyControllerTest
{
    public class DriverDutyControllerTest
    {
        private DriverDutiesController controller;
        private Mock<DriverDutyService> serviceMock;

        [Test]
        public void GetAllDriverDutiesTest()
        {
            this.serviceMock = new Mock<DriverDutyService>();
            this.controller = new DriverDutiesController(serviceMock.Object);
            DriverDutyId aId = new DriverDutyId(Guid.NewGuid());
            DriverDutyId bId = new DriverDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            string[] workBlocksB = { "2" };
            List<DriverDutyDto> list = new List<DriverDutyDto>();
            DriverDutyDto a = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };

            DriverDutyDto b = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "12232CC",
                driverName = "João Silva",
                color = "RGB(1,2,3)",
                type = "seguido",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            Assert.AreEqual(list, controller.GetAll().Result.Value);

        }

        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<DriverDutyService>();
            this.controller = new DriverDutiesController(serviceMock.Object);
            DriverDutyId aId = new DriverDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            DriverDutyDto expected = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<DriverDutyId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            DriverDutyDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);

        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<DriverDutyService>();
            this.controller = new DriverDutiesController(serviceMock.Object);
            DriverDutyId aId = new DriverDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            DriverDutyDto expected = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };
            DriverDutyDto sent = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };




            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<DriverDutyDto>())).Returns(Task.FromResult(expected));
            DriverDutyDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<DriverDutyService>();
            this.controller = new DriverDutiesController(serviceMock.Object);
            DriverDutyId aId = new DriverDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            DriverDutyDto expected = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };
            DriverDutyDto sent = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<DriverDutyId>())).Returns(Task.FromResult(expected));
            DriverDutyDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<DriverDutyService>();
            this.controller = new DriverDutiesController(serviceMock.Object);
            DriverDutyId aId = new DriverDutyId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            DriverDutyDto expected = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };
            DriverDutyDto sent = new DriverDutyDto
            {
                Id = aId.AsString(),
                mecNumber = "122321A",
                driverName = "João Sousa",
                color = "RGB(1,1,1)",
                type = "2 voltas",
                workBlocks = workBlocksA,
                validDate = "2000/10/10"
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<DriverDutyId>())).Returns(Task.FromResult(expected));
            DriverDutyDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
