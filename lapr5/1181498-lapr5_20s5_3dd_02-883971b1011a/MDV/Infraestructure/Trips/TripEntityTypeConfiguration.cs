using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Trips;

namespace DDDSample1.Infrastructure.Trips
{
    internal class TripEntityTypeConfiguration : IEntityTypeConfiguration<Trip>
    {
        public void Configure(EntityTypeBuilder<Trip> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            builder.HasKey(x => x.Id);
            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new TripId(v));
            //builder.Property<bool>("_active").HasColumnName("Active");
            builder.OwnsOne(o => o.tripNumber);
            builder.OwnsOne(o => o.orientation);
            builder.OwnsOne(o => o.line);   
            builder.OwnsMany(o => o.passingTimes, a => 
            {
                a.WithOwner().HasForeignKey("TripId");
                a.Property<int>("number");
                a.HasKey(x => x.Id);
                a.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new TripPassingTimeId(v));
            });



        }
    }
}