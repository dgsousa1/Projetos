using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Vehicles;
using System;

namespace DDDSample1.Infrastructure.Vehicles
{
    internal class VehicleEntityTypeConfiguration : IEntityTypeConfiguration<Vehicle>
    {
        public void Configure(EntityTypeBuilder<Vehicle> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            //builder.HasIndex(k => k.licensePlate).IsUnique();
            builder.HasKey(x => x.Id);

            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(), 
                    v => new VehicleId(v));

            builder.Property(l => l.licensePlate)
                .HasConversion(
                    v => v.licensePlate,
                    v => new VehicleLicensePlate(v));

            builder.HasIndex(s => s.licensePlate).IsUnique(true);

            builder.Property(r => r.VIN)
                .HasConversion(
                    v => v.vin,
                    v => new VehicleVIN(v));

            builder.HasIndex(s => s.VIN).IsUnique(true);

            //builder.Property<bool>("_active").HasColumnName("Active");
        }
    }
}