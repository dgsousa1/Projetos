using System;


namespace DDDSample1.Domain.Drivers
{
    public class DriverSchema
    {
        public String Id { get; set; }

        public string mecNumber { get; set; }

        public string name { get; set; }

        public string birthDate { get; set; }

        public string cc { get; set; }

        public int nif { get; set; }

        public string driverTypes { get; set; }
        public string startDateCompany { get; set; }

        public string finalDateCompany { get; set; }

        public string licenseNumber { get; set; }

        public string licenseDate { get; set; }

        public bool Active { get; set; }
    }
}