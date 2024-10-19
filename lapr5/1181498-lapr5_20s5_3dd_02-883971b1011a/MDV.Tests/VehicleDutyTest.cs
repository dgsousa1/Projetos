using NUnit.Framework;
using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class VehicleDutyTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateVehicleDutySuccess()
        {
            WorkBlockKey key1 = new WorkBlockKey("1");
            WorkBlockKey key2 = new WorkBlockKey("2");
            List<WorkBlockKey> list = new List<WorkBlockKey>();
            list.Add(key1);
            list.Add(key2);
            VehicleDuty vd1 = new VehicleDuty("1","Linha um", list, "2000/10/10", "RGB(1,1,1)");
            Assert.AreEqual("1", vd1.code);
        }

        [Test]
        public void CreateWorkBlockFail()
        {
            WorkBlockKey key1 = new WorkBlockKey("1");
            WorkBlockKey key2 = new WorkBlockKey("2");
            List<WorkBlockKey> list = new List<WorkBlockKey>();
            list.Add(key1);
            list.Add(key2);
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new VehicleDuty("2222222222222222222","Linha 2", list, "2000/10/10", "RGB(1,1,1)"));

        }



    }
}