using System.Linq;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class TripGenDto
    {
        public int NViagens { get; set; }

        public int frequencia { get; set; }

        public int NParalelos { get; set; }

        public string horaSaida { get; set; }
        public string nomeLinha { get; set; }

        public List<PathDto> path { get; set; }

        public bool Active { get; set; }
    }
}