using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Infrastructure.VehicleDuties
{
    internal class VehicleDutyEntityTypeConfiguration : IEntityTypeConfiguration<VehicleDuty>
    {
        public void Configure(EntityTypeBuilder<VehicleDuty> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            builder.HasKey(x => x.Id);
            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new VehicleDutyId(v));
            //builder.Property<bool>("_active").HasColumnName("Active");


            builder.HasIndex(s => s.code).IsUnique(true);     
            builder.HasIndex(s => s.name).IsUnique(true);     


            builder.OwnsMany(o => o.workBlocks, a =>
           {
               a.WithOwner().HasForeignKey("VehicleDutyId");
               a.Property<string>("key");
           });
        }
    }
}