using System;


namespace DDDSample1.Domain.Trips
{
    public class TripPassingTimeDto
    {
        public String Id { get; set; }

        public int number { get; set; }

        public int time { get; set; }

        public string nodeName { get; set; }

        public bool isUsed { get;  set; }

        public bool isReliefPoint { get; set; }
    }
}