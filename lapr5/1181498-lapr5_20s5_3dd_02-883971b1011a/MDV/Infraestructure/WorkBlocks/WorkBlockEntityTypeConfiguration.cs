using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.WorkBlocks;
using System;

namespace DDDSample1.Infrastructure.WorkBlocks
{
    internal class WorkBlockEntityTypeConfiguration : IEntityTypeConfiguration<WorkBlock>
    {
        public void Configure(EntityTypeBuilder<WorkBlock> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            //builder.HasIndex(k => k.licensePlate).IsUnique();
            builder.HasKey(x => x.Id);
            builder.Property(x => x.Id)
               .HasConversion(
                   v => v.AsString(),
                   v => new WorkBlockId(v));


            builder.OwnsMany(o => o.trips, a =>
            {
                a.WithOwner().HasForeignKey("WorkBlockId");
                a.Property<int>("key");
            });



            builder.Property(r => r.key)
            .HasConversion(
                v => v.key,
                v => new WorkBlockKey(v));

            builder.HasIndex(s => s.key).IsUnique(true);
        }
    }
}