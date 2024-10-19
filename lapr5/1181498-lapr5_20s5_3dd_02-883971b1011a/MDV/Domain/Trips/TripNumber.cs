using System;
using DDDSample1.Domain.Shared;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class TripNumber : ValueObject
    {

        public int key { get; set; }

        private TripNumber()
        {

        }

        public TripNumber(int value)
        {
            this.key = value;
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return key;
        }
    }
}