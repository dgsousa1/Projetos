using NUnit.Framework;
using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Shared;

namespace MDV.Tests
{
    public class WorkBlockTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateWorkBlockSuccess()
        {
            TripNumber n1 = new TripNumber(1);
            TripNumber n2 = new TripNumber(2);
            List<TripNumber> list = new List<TripNumber>();
            list.Add(n1);
            list.Add(n2);
            WorkBlock wb1 = new WorkBlock("1", "2000/10/9", "2000/10/10", list);
            Assert.AreEqual("1", wb1.key.key);
        }

        [Test]
        public void CreateWorkBlockFail()
        {
            TripNumber n1 = new TripNumber(3);
            TripNumber n2 = new TripNumber(4);
            List<TripNumber> list = new List<TripNumber>();
            list.Add(n1);
            list.Add(n2);
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new WorkBlock("2", "2000/10/11", "2000/10/10", list));

        }



    }
}