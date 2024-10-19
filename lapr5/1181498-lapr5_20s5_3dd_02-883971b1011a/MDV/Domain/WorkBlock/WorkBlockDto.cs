using System;


namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockDto
    {
        public String Id { get; set; }

        public string key { get; set; }
        public string startInstant { get; set; }

        public string endInstant { get; set; }

        public int[] trips { get; set; }
        public bool Active { get; set; }
    }
}