using System;
using Moq;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace WorkBlockControllerTest
{
    public class WorkBlockControllerTest
    {
    
        private WorkBlocksController controller;
        private Mock<WorkBlockService> serviceMock;

        [Test]
        public void GetAllWorkBlocksTest()
        {
            this.serviceMock = new Mock<WorkBlockService>();
            this.controller = new WorkBlocksController(serviceMock.Object);
            WorkBlockId aId = new WorkBlockId(Guid.NewGuid());
            WorkBlockId bId = new WorkBlockId(Guid.NewGuid());
            List<WorkBlockDto> list = new List<WorkBlockDto>();
            int[] aL = { 1, 2 };
            int[] bL = { 3, 4 };


            WorkBlockDto a = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };
            WorkBlockDto b = new WorkBlockDto
            {
                Id = bId.AsString(),
                startInstant = "2020/10/19",
                key = "1",
                endInstant = "2020/10/20",
                trips = bL
            };

            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            Assert.AreEqual(list, controller.GetAll().Result.Value);
        }

        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<WorkBlockService>();
            this.controller = new WorkBlocksController(serviceMock.Object);
            WorkBlockId aId = new WorkBlockId(Guid.NewGuid());
            List<WorkBlockDto> list = new List<WorkBlockDto>();
            int[] aL = { 1, 2 };

            WorkBlockDto expected = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<WorkBlockId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            WorkBlockDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);

        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<WorkBlockService>();
            this.controller = new WorkBlocksController(serviceMock.Object);
            WorkBlockId aId = new WorkBlockId(Guid.NewGuid());
            List<WorkBlockDto> list = new List<WorkBlockDto>();
            int[] aL = { 1, 2 };

            WorkBlockDto expected = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };
            WorkBlockDto sent = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };


            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<WorkBlockDto>())).Returns(Task.FromResult(expected));
            WorkBlockDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<WorkBlockService>();
            this.controller = new WorkBlocksController(serviceMock.Object);
            WorkBlockId aId = new WorkBlockId(Guid.NewGuid());
            List<WorkBlockDto> list = new List<WorkBlockDto>();
            int[] aL = { 1, 2 };

            WorkBlockDto expected = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };
            WorkBlockDto sent = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<WorkBlockId>())).Returns(Task.FromResult(expected));
            WorkBlockDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<WorkBlockService>();
            this.controller = new WorkBlocksController(serviceMock.Object);
            WorkBlockId aId = new WorkBlockId(Guid.NewGuid());
            List<WorkBlockDto> list = new List<WorkBlockDto>();
            int[] aL = { 1, 2 };

            WorkBlockDto expected = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };
            WorkBlockDto sent = new WorkBlockDto
            {
                Id = aId.AsString(),
                startInstant = "2000/10/9",
                key = "1",
                endInstant = "2000/10/10",
                trips = aL
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<WorkBlockId>())).Returns(Task.FromResult(expected));
            WorkBlockDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
