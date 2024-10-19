namespace DDDSample1.Domain.Trips
{
    public class PathNodesDto
    {
        public int duracao { get; set; }

        public int distancia { get; set; }

        public string inicialNode { get; set; }

        public string finalNode { get; set; }

        public bool Active { get; set; }
    }
}