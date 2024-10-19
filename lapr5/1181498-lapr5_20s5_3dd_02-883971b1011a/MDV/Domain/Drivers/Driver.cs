using DDDSample1.Domain.Shared;
using System;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;

namespace DDDSample1.Domain.Drivers
{
    public class Driver : Entity<DriverId>, IAggregateRoot
    {
        [Required]
        public MechanicalNumber mecNumber { get; private set; }
        public string name { get; private set; }

        public DateTime birthDate { get; private set; }

        public DriverCC cc { get; private set; }

        public NIF nif { get; private set; }

        public string driverTypes { get; private set; }

        public DateTime startDateCompany { get; private set; }

        public DateTime finalDateCompany { get; private set; }

        public DriverLicense driverLicense { get; private set; }

        public bool Active { get; private set; }

        private Regex ccRegex = new Regex("^[0-9]{8}$");

        private Driver()
        {
            this.Active = true;
        }

        public Driver(string mecNumber, string name, string birthDate,
        string cc, int nif, string driverTypes, string startDateCompany, string finalDateCompany,
        string licenseNumber, string licenseDate)
        {

            this.Id = new DriverId(Guid.NewGuid());

            this.mecNumber = new MechanicalNumber(mecNumber);

            this.name = name;

            DateTime birthday = Convert.ToDateTime(birthDate);
            DateTime now = DateTime.Today;
            int age = now.Year - birthday.Year;
            if (age < Settings.MIN_AGE)
            {
                throw new BusinessRuleValidationException("Driver is younger than"+Settings.MIN_AGE+"years old!");
            }

            this.birthDate = birthday;
            this.cc = new DriverCC(cc);

            this.nif = new NIF(nif);
            this.driverTypes = driverTypes;
            this.startDateCompany = Convert.ToDateTime(startDateCompany);
            this.finalDateCompany = Convert.ToDateTime(finalDateCompany);

            this.driverLicense = new DriverLicense(licenseNumber, licenseDate);
        }
        public void ChangeName(string name)
        {
            if (!this.Active)
                throw new BusinessRuleValidationException("It is not possible to change the name to an inactive Driver.");
            this.name = name;
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }
    }
}