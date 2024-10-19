using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using DDDSample1.Infrastructure;
using DDDSample1.Infrastructure.Shared;
using DDDSample1.Infrastructure.Trips;
using DDDSample1.Infrastructure.VehicleDuties;
using DDDSample1.Infrastructure.DriverDuties;
using DDDSample1.Infrastructure.Drivers;
using DDDSample1.Infrastructure.Vehicles;
using DDDSample1.Infrastructure.WorkBlocks;
using DDDSample1.Infrastructure.Users;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Imports;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.Drivers;
using DDDSample1.Domain.Vehicles;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.Users;
using System.Text;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace DDDSample1
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            services.AddDbContext<DDDSample1DbContext>(opt =>
                opt.UseSqlServer(Configuration.GetConnectionString("DefaultConnection")));

            ConfigureMyServices(services);
            services.AddCors();
            services.AddControllers().AddNewtonsoftJson();
            var key = Encoding.ASCII.GetBytes(Settings.Secret);
            services.AddAuthentication(x =>
            {
                x.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
                x.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            })
            .AddJwtBearer(x =>
            {
                x.RequireHttpsMetadata = false;
                x.SaveToken = true;
                x.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(key),
                    ValidateIssuer = false,
                    ValidateAudience = false
                };
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseCors(x => x
                 .AllowAnyOrigin()
                 .AllowAnyMethod()
                 .AllowAnyHeader());

            //app.UseHttpsRedirection();
            //app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        public void ConfigureMyServices(IServiceCollection services)
        {
            services.AddTransient<IUnitOfWork, UnitOfWork>();

            services.AddTransient<ITripRepository, TripRepository>();
            services.AddTransient<TripService>();

            services.AddTransient<IDriverRepository, DriverRepository>();
            services.AddTransient<DriverService>();

            services.AddTransient<IVehicleRepository, VehicleRepository>();
            services.AddTransient<VehicleService>();

            services.AddTransient<IWorkBlockRepository, WorkBlockRepository>();
            services.AddTransient<WorkBlockService>();

            services.AddTransient<IUserRepository, UserRepository>();
            services.AddTransient<UserService>();

            services.AddTransient<IVehicleDutyRepository, VehicleDutyRepository>();
            services.AddTransient<VehicleDutyService>();

            services.AddTransient<IDriverDutyRepository, DriverDutyRepository>();
            services.AddTransient<DriverDutyService>();

            services.AddTransient<ImportService>();
        }
    }
}
