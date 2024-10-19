using Microsoft.EntityFrameworkCore;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Drivers;
using DDDSample1.Domain.Vehicles;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Domain.Users;
using DDDSample1.Infrastructure.Trips;
using DDDSample1.Infrastructure.Drivers;
using DDDSample1.Infrastructure.Vehicles;
using DDDSample1.Infrastructure.WorkBlocks;
using DDDSample1.Infrastructure.Users;
using DDDSample1.Infrastructure.VehicleDuties;
using DDDSample1.Infrastructure.DriverDuties;


namespace DDDSample1.Infrastructure
{
    public class DDDSample1DbContext : DbContext
    {

        public DbSet<Trip> Trips { get; set; }

        public DbSet<Driver> Drivers { get; set; }

        public DbSet<Vehicle> Vehicles { get; set; }

        public DbSet<WorkBlock> WorkBlocks { get; set; }

        public DbSet<User> Users { get; set; }

        public DbSet<VehicleDuty> VehicleDuties { get; set; }

        public DbSet<DriverDuty> DriverDuties { get; set; }

        public DDDSample1DbContext(DbContextOptions options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.ApplyConfiguration(new TripEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new DriverEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new VehicleEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new WorkBlockEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new UserEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new VehicleDutyEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new DriverDutyEntityTypeConfiguration());

        }
    }
}