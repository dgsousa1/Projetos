using System;
using DDDSample1.Domain.WorkBlocks;
using System.Collections.Generic;


namespace DDDSample1.Domain.VehicleDuties
{
    public class VehicleDutySchema
    {
        public String Id { get; set; }
        public string code { get; set; }
        public string[] workBlocks { get; set; }
        public int duration { get; set; }
        public string validDate { get; set; }
        public string color { get; set; }
        public bool Active { get; set; }
    }
}