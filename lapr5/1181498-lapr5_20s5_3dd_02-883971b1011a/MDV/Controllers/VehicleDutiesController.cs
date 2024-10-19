using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.VehicleDuties;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VehicleDutiesController : ControllerBase
    {
        private readonly VehicleDutyService _service;

        public VehicleDutiesController(VehicleDutyService service)
        {
            _service = service;
        }

        // GET: api/VehicleDuties
        [HttpGet]
        public async Task<ActionResult<IEnumerable<VehicleDutyDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/VehicleDuties/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<VehicleDutyDto>> GetGetById(String id)
        {
            var Vehicle = await _service.GetByIdAsync(new VehicleDutyId(id));

            if (Vehicle == null)
            {
                return NotFound();
            }

            return Vehicle;
        }

        // POST: api/VehicleDuties
        [HttpPost]
        public async Task<ActionResult<VehicleDutyDto>> Create(VehicleDutyDto dto)
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


        // PUT: api/VehicleDuties/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<VehicleDutyDto>> Update(String id, VehicleDutyDto dto)
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

        // Inactivate: api/VehicleDuties/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<VehicleDutyDto>> SoftDelete(String id)
        {
            var Vehicle = await _service.InactivateAsync(new VehicleDutyId(id));

            if (Vehicle == null)
            {
                return NotFound();
            }

            return Vehicle;
        }

        // DELETE: api/VehicleDuties/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<VehicleDutyDto>> HardDelete(String id)
        {
            try
            {
                var Vehicle = await _service.DeleteAsync(new VehicleDutyId(id));

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