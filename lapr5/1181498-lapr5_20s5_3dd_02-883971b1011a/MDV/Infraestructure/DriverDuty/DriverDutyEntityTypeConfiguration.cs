using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Infrastructure.DriverDuties
{
    internal class DriverDutyEntityTypeConfiguration : IEntityTypeConfiguration<DriverDuty>
    {
        public void Configure(EntityTypeBuilder<DriverDuty> builder)
        {
            builder.HasKey(x => x.Id);
            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new DriverDutyId(v));


            builder.HasIndex(s => s.mecNumber).IsUnique(true);

            builder.OwnsOne(o => o.type);

            builder.OwnsMany(o => o.workBlocks, a =>
           {
               a.WithOwner().HasForeignKey("DriverDutyId");
               a.Property<string>("key");
           });
        }
    }
}