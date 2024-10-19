namespace DDDSample1.Domain.Trips
{
    public class TripAdhocDto
    {
        public int numeroViagem { get; set; }

        public string horaSaida { get; set; }

        public string orientation { get; set; }

        public string nomeLinha { get; set; }

        public PathDto path { get; set; }

        public bool Active { get; set; }
    }
}