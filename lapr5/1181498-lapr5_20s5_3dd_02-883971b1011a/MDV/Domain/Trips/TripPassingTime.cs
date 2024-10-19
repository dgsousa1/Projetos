using System;
using DDDSample1.Domain.Shared;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class TripPassingTime : Entity<TripPassingTimeId>
    {

        public int number { get; private set; }

        public int time { get; private set; }

        public string nodeName { get; private set; }

        public bool isUsed { get; private set; }

        public bool isReliefPoint { get; private set; }

        private TripPassingTime()
        {
        }

        public TripPassingTime(int number, int time, string nodeName, bool isUsed, bool isReliefPoint)
        {
            this.Id = new TripPassingTimeId(Guid.NewGuid());
            this.number = number;
            this.time = time;
            this.nodeName = nodeName;
            this.isUsed = isUsed;
            this.isReliefPoint = isReliefPoint;
        }


    }
}