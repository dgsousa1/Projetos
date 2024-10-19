using NUnit.Framework;
using System;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Shared;
using System.Collections.Generic;


namespace MDV.Tests
{
    public class TripTest
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void CreateTripSuccess()
        {
            TripPassingTime pt1 = new TripPassingTime(12, 2000, "Teste1", false, false);
            TripPassingTime pt2 = new TripPassingTime(12, 2000, "Teste1", false, false);
            List<TripPassingTime> list = new List<TripPassingTime>();
            list.Add(pt1);
            list.Add(pt2);
            Trip t1 = new Trip(1, "Go", "1", 2, false, list);
            Assert.AreEqual(1, t1.tripNumber.key);

        }

        [Test]
        public void CreateTripFail()
        {
            TripPassingTime pt1 = new TripPassingTime(12, 2000, "Teste1", false, false);
            TripPassingTime pt2 = new TripPassingTime(12, 2000, "Teste1", false, false);
            List<TripPassingTime> list = new List<TripPassingTime>();
            list.Add(pt1);
            list.Add(pt2);
            Assert.Throws<DDDSample1.Domain.Shared.BusinessRuleValidationException>(() => new Trip(1, "FAIL", "1", 2, false, list));
        }

    }
}