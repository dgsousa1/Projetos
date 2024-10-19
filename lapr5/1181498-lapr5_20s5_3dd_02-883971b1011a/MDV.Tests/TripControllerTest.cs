using System;
using Moq;
using DDDSample1.Domain.Trips;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace TripControllerTest
{
    public class TripControllerTest
    {
        private TripsController controller;
        private Mock<TripService> serviceMock;

        [Test]
        public void GetAllTripsTest()
        {
            this.serviceMock = new Mock<TripService>();
            this.controller = new TripsController(serviceMock.Object);
            TripId aId = new TripId(Guid.NewGuid());
            TripId bId = new TripId(Guid.NewGuid());

            List<TripDto> list = new List<TripDto>();
            List<TripPassingTimeDto> ptList = new List<TripPassingTimeDto>();
            List<TripPassingTimeDto> ptList2 = new List<TripPassingTimeDto>();

            TripPassingTimeDto t = new TripPassingTimeDto
            {
                number = 12,
                time = 2000,
                nodeName = "Teste1",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto t2 = new TripPassingTimeDto
            {
                number = 24,
                time = 2000,
                nodeName = "Teste2",
                isUsed = false,
                isReliefPoint = false
            };
            ptList.Add(t);
            ptList.Add(t2);

            TripDto a = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };

            TripPassingTimeDto z = new TripPassingTimeDto
            {
                number = 14,
                time = 2500,
                nodeName = "Teste3",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto z2 = new TripPassingTimeDto
            {
                number = 34,
                time = 3000,
                nodeName = "Teste4",
                isUsed = false,
                isReliefPoint = false
            };
            ptList2.Add(t);
            ptList2.Add(t2);

            TripDto b = new TripDto
            {
                Id = bId.AsString(),
                tripNumber = 3,
                orientation = "Return",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList2
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));

            Assert.AreEqual(list, controller.GetAll().Result.Value);

        }

        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<TripService>();
            this.controller = new TripsController(serviceMock.Object);
            TripId aId = new TripId(Guid.NewGuid());
            List<TripPassingTimeDto> ptList = new List<TripPassingTimeDto>();

            TripPassingTimeDto t = new TripPassingTimeDto
            {
                number = 12,
                time = 2000,
                nodeName = "Teste1",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto t2 = new TripPassingTimeDto
            {
                number = 24,
                time = 2000,
                nodeName = "Teste2",
                isUsed = false,
                isReliefPoint = false
            };
            ptList.Add(t);
            ptList.Add(t2);

            TripDto expected = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<TripId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            TripDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);
        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<TripService>();
            this.controller = new TripsController(serviceMock.Object);
            TripId aId = new TripId(Guid.NewGuid());
            List<TripPassingTimeDto> ptList = new List<TripPassingTimeDto>();

            TripPassingTimeDto t = new TripPassingTimeDto
            {
                number = 12,
                time = 2000,
                nodeName = "Teste1",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto t2 = new TripPassingTimeDto
            {
                number = 24,
                time = 2000,
                nodeName = "Teste2",
                isUsed = false,
                isReliefPoint = false
            };
            ptList.Add(t);
            ptList.Add(t2);

            TripDto expected = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };
            TripDto sent = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };

            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<TripDto>())).Returns(Task.FromResult(expected));
            TripDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<TripService>();
            this.controller = new TripsController(serviceMock.Object);
            TripId aId = new TripId(Guid.NewGuid());
            List<TripPassingTimeDto> ptList = new List<TripPassingTimeDto>();

            TripPassingTimeDto t = new TripPassingTimeDto
            {
                number = 12,
                time = 2000,
                nodeName = "Teste1",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto t2 = new TripPassingTimeDto
            {
                number = 24,
                time = 2000,
                nodeName = "Teste2",
                isUsed = false,
                isReliefPoint = false
            };
            ptList.Add(t);
            ptList.Add(t2);

            TripDto expected = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };
            TripDto sent = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<TripId>())).Returns(Task.FromResult(expected));
            TripDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }


        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<TripService>();
            this.controller = new TripsController(serviceMock.Object);
            TripId aId = new TripId(Guid.NewGuid());
            List<TripPassingTimeDto> ptList = new List<TripPassingTimeDto>();

            TripPassingTimeDto t = new TripPassingTimeDto
            {
                number = 12,
                time = 2000,
                nodeName = "Teste1",
                isUsed = false,
                isReliefPoint = false
            };
            TripPassingTimeDto t2 = new TripPassingTimeDto
            {
                number = 24,
                time = 2000,
                nodeName = "Teste2",
                isUsed = false,
                isReliefPoint = false
            };
            ptList.Add(t);
            ptList.Add(t2);

            TripDto expected = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };
            TripDto sent = new TripDto
            {
                Id = aId.AsString(),
                tripNumber = 1,
                orientation = "Go",
                line = "1",
                path = 2,
                isGenerated = false,
                passingTimes = ptList
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<TripId>())).Returns(Task.FromResult(expected));
            TripDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
