using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Drivers;

namespace DDDSample1.Infrastructure.Drivers
{
    internal class DriverEntityTypeConfiguration : IEntityTypeConfiguration<Driver>
    {
        public void Configure(EntityTypeBuilder<Driver> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            builder.HasKey(x => x.Id);
            builder.HasIndex(z => z.mecNumber).IsUnique(true);

            builder.Property(r => r.cc)
                .HasConversion(
                    v => v.cc,
                    v => new DriverCC(v));

            builder.HasIndex(s => s.cc).IsUnique(true);

            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new DriverId(v));

            builder.Property(r => r.mecNumber)
                           .HasConversion(
                               v => v.mecNumber,
                               v => new MechanicalNumber(v));

            builder.HasIndex(s => s.mecNumber).IsUnique(true);

            builder.Property(r => r.nif)
               .HasConversion(
                   v => v.nif,
                   v => new NIF(v));

            builder.HasIndex(s => s.nif).IsUnique(true);

            builder.OwnsOne(o => o.driverLicense);

            //builder.Property<bool>("_active").HasColumnName("Active");
        }
    }
}