using System;
using DDDSample1.Domain.WorkBlocks;
using System.Collections.Generic;


namespace DDDSample1.Domain.DriverDuties
{
    public class DriverDutyDto
    {
        public String Id { get; set; }
        public string mecNumber { get; set; }
        public string driverName { get; set; }
        public string color { get; set; }
        public string type { get; set; }
        public string[] workBlocks { get; set; }
        public int duration { get; set; }
        public string validDate { get; set; }
        public bool Active { get; set; }
    }
}