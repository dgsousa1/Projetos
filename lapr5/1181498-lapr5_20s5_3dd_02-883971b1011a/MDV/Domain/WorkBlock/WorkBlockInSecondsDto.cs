using System;


namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockInSecondsDto
    {
        public String Id { get; set; }

        public string key { get; set; }
        public int startInstant { get; set; }

        public int endInstant { get; set; }

        public int[] trips { get; set; }
        public bool Active { get; set; }
    }
}