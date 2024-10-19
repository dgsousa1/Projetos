using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Users;
using DDDSample1.Domain.Drivers;


namespace DDDSample1.Infrastructure.Users
{
    internal class UserEntityTypeConfiguration : IEntityTypeConfiguration<User>
    {
        public void Configure(EntityTypeBuilder<User> builder)
        {
            //builder.ToTable("Families", SchemaNames.DDDSample1);
            builder.HasKey(x => x.Id);
           
            builder.Property(x => x.Id)
                .HasConversion(
                    v => v.AsString(),
                    v => new UserId(v));

            builder.Property(r => r.nif)
               .HasConversion(
                   v => v.nif,
                   v => new NIF(v));

            builder.HasIndex(s => s.nif).IsUnique(true);     

            builder.Property(r => r.password)
               .HasConversion(
                   v => v.password,
                   v => new Password(v));

            builder.Property(r => r.telephone)
               .HasConversion(
                   v => v.telephone,
                   v => new Telephone(v));

            builder.HasIndex(s => s.telephone).IsUnique(true); 

            builder.Property(r => r.email)
               .HasConversion(
                   v => v.email,
                   v => new Email(v));

            builder.HasIndex(s => s.email).IsUnique(true);

            builder.Property(r => r.nif)
               .HasConversion(
                   v => v.nif,
                   v => new NIF(v));

            builder.HasIndex(s => s.nif).IsUnique(true); 
        
        }
    }
}