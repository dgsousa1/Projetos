using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Vehicles;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VehiclesController : ControllerBase
    {
        private readonly VehicleService _service;

        public VehiclesController(VehicleService service)
        {
            _service = service;
        }

        // GET: api/Vehicles
        [HttpGet]
        public async Task<ActionResult<IEnumerable<VehicleDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Vehicles/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<VehicleDto>> GetGetById(String id)
        {
            var Vehicle = await _service.GetByIdAsync(new VehicleId(id));

            if (Vehicle == null)
            {
                return NotFound();
            }

            return Vehicle;
        }

        // POST: api/Vehicles
        [HttpPost]
        public async Task<ActionResult<VehicleDto>> Create(VehicleDto dto)
        {
            try
            {
                var Vehicle = await _service.AddAsync(dto);
                return CreatedAtAction(nameof(GetGetById), new { id = Vehicle.Id }, Vehicle);
            }
            catch (Exception e)
            {
                if (e is ArgumentException || e is BusinessRuleValidationException)
                {
                    return BadRequest(new { Message = e.Message });
                }
                throw;
            }
        }


        // PUT: api/Vehicles/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<VehicleDto>> Update(String id, VehicleDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var Vehicle = await _service.UpdateAsync(dto);

                if (Vehicle == null)
                {
                    return NotFound();
                }
                return Vehicle;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/Vehicles/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<VehicleDto>> SoftDelete(String id)
        {
            var Vehicle = await _service.InactivateAsync(new VehicleId(id));

            if (Vehicle == null)
            {
                return NotFound();
            }

            return Vehicle;
        }

        // DELETE: api/Vehicles/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<VehicleDto>> HardDelete(String id)
        {
            try
            {
                var Vehicle = await _service.DeleteAsync(new VehicleId(id));

                if (Vehicle == null)
                {
                    return NotFound();
                }

                return Vehicle;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}