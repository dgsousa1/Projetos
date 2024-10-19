using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DDDNetCore.Migrations
{
    public partial class azuremigration : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Drivers",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    mecNumber = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    birthDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    cc = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    nif = table.Column<int>(type: "int", nullable: true),
                    driverTypes = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    startDateCompany = table.Column<DateTime>(type: "datetime2", nullable: false),
                    finalDateCompany = table.Column<DateTime>(type: "datetime2", nullable: false),
                    driverLicense_licenseNumber = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    driverLicense_emissionDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Drivers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Trips",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    tripNumber_key = table.Column<int>(type: "int", nullable: true),
                    orientation_orientation = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    line_lineName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    path = table.Column<int>(type: "int", nullable: false),
                    isGenerated = table.Column<bool>(type: "bit", nullable: false),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Trips", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    username = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    password = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    telephone = table.Column<int>(type: "int", nullable: true),
                    email = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    nif = table.Column<int>(type: "int", nullable: true),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "VehicleDuties",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    code = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    duration = table.Column<int>(type: "int", nullable: false),
                    validDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    color = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_VehicleDuties", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Vehicles",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    licensePlate = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    VIN = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    type = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    startDateService = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Vehicles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "WorkBlocks",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    startInstant = table.Column<DateTime>(type: "datetime2", nullable: false),
                    endInstant = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Active = table.Column<bool>(type: "bit", nullable: false),
                    key = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WorkBlocks", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "TripPassingTime",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    number = table.Column<int>(type: "int", nullable: false),
                    time = table.Column<int>(type: "int", nullable: false),
                    nodeName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    isUsed = table.Column<bool>(type: "bit", nullable: false),
                    isReliefPoint = table.Column<bool>(type: "bit", nullable: false),
                    TripId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TripPassingTime", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TripPassingTime_Trips_TripId",
                        column: x => x.TripId,
                        principalTable: "Trips",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "WorkBlockKey",
                columns: table => new
                {
                    VehicleDutyId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    key = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WorkBlockKey", x => new { x.VehicleDutyId, x.Id });
                    table.ForeignKey(
                        name: "FK_WorkBlockKey_VehicleDuties_VehicleDutyId",
                        column: x => x.VehicleDutyId,
                        principalTable: "VehicleDuties",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "WorkBlocks_trips",
                columns: table => new
                {
                    WorkBlockId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    key = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WorkBlocks_trips", x => new { x.WorkBlockId, x.Id });
                    table.ForeignKey(
                        name: "FK_WorkBlocks_trips_WorkBlocks_WorkBlockId",
                        column: x => x.WorkBlockId,
                        principalTable: "WorkBlocks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Drivers_cc",
                table: "Drivers",
                column: "cc",
                unique: true,
                filter: "[cc] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Drivers_mecNumber",
                table: "Drivers",
                column: "mecNumber",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_Drivers_nif",
                table: "Drivers",
                column: "nif",
                unique: true,
                filter: "[nif] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_TripPassingTime_TripId",
                table: "TripPassingTime",
                column: "TripId");

            migrationBuilder.CreateIndex(
                name: "IX_Users_email",
                table: "Users",
                column: "email",
                unique: true,
                filter: "[email] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Users_nif",
                table: "Users",
                column: "nif",
                unique: true,
                filter: "[nif] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Users_telephone",
                table: "Users",
                column: "telephone",
                unique: true,
                filter: "[telephone] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_VehicleDuties_code",
                table: "VehicleDuties",
                column: "code",
                unique: true,
                filter: "[code] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Vehicles_licensePlate",
                table: "Vehicles",
                column: "licensePlate",
                unique: true,
                filter: "[licensePlate] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Vehicles_VIN",
                table: "Vehicles",
                column: "VIN",
                unique: true,
                filter: "[VIN] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_WorkBlocks_key",
                table: "WorkBlocks",
                column: "key",
                unique: true,
                filter: "[key] IS NOT NULL");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Drivers");

            migrationBuilder.DropTable(
                name: "TripPassingTime");

            migrationBuilder.DropTable(
                name: "Users");

            migrationBuilder.DropTable(
                name: "Vehicles");

            migrationBuilder.DropTable(
                name: "WorkBlockKey");

            migrationBuilder.DropTable(
                name: "WorkBlocks_trips");

            migrationBuilder.DropTable(
                name: "Trips");

            migrationBuilder.DropTable(
                name: "VehicleDuties");

            migrationBuilder.DropTable(
                name: "WorkBlocks");
        }
    }
}
