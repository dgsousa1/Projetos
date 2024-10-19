using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DDDNetCore.Migrations
{
    public partial class addDriverDuty : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_WorkBlockKey_VehicleDuties_VehicleDutyId",
                table: "WorkBlockKey");

            migrationBuilder.DropPrimaryKey(
                name: "PK_WorkBlockKey",
                table: "WorkBlockKey");

            migrationBuilder.RenameTable(
                name: "WorkBlockKey",
                newName: "VehicleDuties_workBlocks");

            migrationBuilder.AddPrimaryKey(
                name: "PK_VehicleDuties_workBlocks",
                table: "VehicleDuties_workBlocks",
                columns: new[] { "VehicleDutyId", "Id" });

            migrationBuilder.CreateTable(
                name: "DriverDuties",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    mecNumber = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    driverName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    color = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    type_type = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    duration = table.Column<int>(type: "int", nullable: false),
                    validDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DriverDuties", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "DriverDuties_workBlocks",
                columns: table => new
                {
                    DriverDutyId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    key = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DriverDuties_workBlocks", x => new { x.DriverDutyId, x.Id });
                    table.ForeignKey(
                        name: "FK_DriverDuties_workBlocks_DriverDuties_DriverDutyId",
                        column: x => x.DriverDutyId,
                        principalTable: "DriverDuties",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_DriverDuties_mecNumber",
                table: "DriverDuties",
                column: "mecNumber",
                unique: true,
                filter: "[mecNumber] IS NOT NULL");

            migrationBuilder.AddForeignKey(
                name: "FK_VehicleDuties_workBlocks_VehicleDuties_VehicleDutyId",
                table: "VehicleDuties_workBlocks",
                column: "VehicleDutyId",
                principalTable: "VehicleDuties",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_VehicleDuties_workBlocks_VehicleDuties_VehicleDutyId",
                table: "VehicleDuties_workBlocks");

            migrationBuilder.DropTable(
                name: "DriverDuties_workBlocks");

            migrationBuilder.DropTable(
                name: "DriverDuties");

            migrationBuilder.DropPrimaryKey(
                name: "PK_VehicleDuties_workBlocks",
                table: "VehicleDuties_workBlocks");

            migrationBuilder.RenameTable(
                name: "VehicleDuties_workBlocks",
                newName: "WorkBlockKey");

            migrationBuilder.AddPrimaryKey(
                name: "PK_WorkBlockKey",
                table: "WorkBlockKey",
                columns: new[] { "VehicleDutyId", "Id" });

            migrationBuilder.AddForeignKey(
                name: "FK_WorkBlockKey_VehicleDuties_VehicleDutyId",
                table: "WorkBlockKey",
                column: "VehicleDutyId",
                principalTable: "VehicleDuties",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
