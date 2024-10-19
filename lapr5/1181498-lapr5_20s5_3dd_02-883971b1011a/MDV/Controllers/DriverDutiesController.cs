using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.DriverDuties;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DriverDutiesController : ControllerBase
    {
        private readonly DriverDutyService _service;

        public DriverDutiesController(DriverDutyService service)
        {
            _service = service;
        }

        // GET: api/DriverDuties
        [HttpGet]
        public async Task<ActionResult<IEnumerable<DriverDutyDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/DriverDuties/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<DriverDutyDto>> GetGetById(String id)
        {
            var DriverDuty = await _service.GetByIdAsync(new DriverDutyId(id));

            if (DriverDuty == null)
            {
                return NotFound();
            }

            return DriverDuty;
        }

        // POST: api/DriverDuties
        [HttpPost]
        public async Task<ActionResult<DriverDutyDto>> Create(DriverDutyDto dto)
        {
            try
            {
                var DriverDuty = await _service.AddAsync(dto);
                return CreatedAtAction(nameof(GetGetById), new { id = DriverDuty.Id }, DriverDuty);
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


        // PUT: api/DriverDuties/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<DriverDutyDto>> Update(String id, DriverDutyDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var DriverDuty = await _service.UpdateAsync(dto);

                if (DriverDuty == null)
                {
                    return NotFound();
                }
                return DriverDuty;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/DriverDuties/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<DriverDutyDto>> SoftDelete(String id)
        {
            var DriverDuty = await _service.InactivateAsync(new DriverDutyId(id));

            if (DriverDuty == null)
            {
                return NotFound();
            }

            return DriverDuty;
        }

        // DELETE: api/DriverDuties/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<DriverDutyDto>> HardDelete(String id)
        {
            try
            {
                var DriverDuty = await _service.DeleteAsync(new DriverDutyId(id));

                if (DriverDuty == null)
                {
                    return NotFound();
                }

                return DriverDuty;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}
