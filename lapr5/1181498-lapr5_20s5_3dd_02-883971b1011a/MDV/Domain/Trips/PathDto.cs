using System;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class PathDto
    {
        public int key { get; set; }

        public List<PathNodesDto> pathNodes { get; set; }

        public bool Active { get; set; }
    }
}