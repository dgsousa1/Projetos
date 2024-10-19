using NUnit.Framework;
using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class DriverDutyTest
    {
        List<WorkBlockKey> list;

        [SetUp]
        public void Setup()
        {
            WorkBlockKey key1 = new WorkBlockKey("1");
            WorkBlockKey key2 = new WorkBlockKey("2");
            this.list = new List<WorkBlockKey>();
            list.Add(key1);
            list.Add(key2);
        }

        [Test]
        public void CreateDriverDutySuccess()
        {
            DriverDuty dd1 = new DriverDuty("A111", "Joao Sousa", "RGB(1,1,1)", "2 voltas", list, "2021/02/20");
            Assert.AreEqual("Joao Sousa", dd1.driverName);
        }

        [Test]
        public void CreateDriverDutyFailMecNumber()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new DriverDuty("A111123456789", "Joao Sousa", "RGB(1,1,1)", "2 voltas", list, "2021/02/20"));

        }

        [Test]
        public void CreateDriverDutyFailDriverName()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new DriverDuty("A111", "Joao Sousa22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222", "RGB(1,1,1)", "2 voltas", list, "2021/02/20"));

        }

        [Test]
        public void CreateDriverDutyFailDriverType()
        {
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new DriverDuty("A111", "Joao Sousa", "RGB(1,1,1)", "2 voltas22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222", list, "2021/02/20"));

        }




    }
}