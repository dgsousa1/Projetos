using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Drivers;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DriversController : ControllerBase
    {
        private readonly DriverService _service;

        public DriversController(DriverService service)
        {
            _service = service;
        }

        // GET: api/Drivers
        [HttpGet]
        public async Task<ActionResult<IEnumerable<DriverDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Drivers/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<DriverDto>> GetGetById(String id)
        {
            var Driver = await _service.GetByIdAsync(new DriverId(id));

            if (Driver == null)
            {
                return NotFound();
            }

            return Driver;
        }

        // POST: api/Drivers
        [HttpPost]
        public async Task<ActionResult<DriverDto>> Create(DriverDto dto)
        {
            try
            {
                var Driver = await _service.AddAsync(dto);
                return CreatedAtAction(nameof(GetGetById), new { id = Driver.Id }, Driver);
            }
            catch (Exception e)
            {
                if (e is ArgumentException || e is BusinessRuleValidationException || e is Exception)
                {
                    return BadRequest(new { Message = e.Message });
                }
                throw;
            }
        }

        // PUT: api/Drivers/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<DriverDto>> Update(String id, DriverDto dto)
        {
            if (!id.Equals(dto.Id))
            {
                return BadRequest();
            }

            try
            {
                var Driver = await _service.UpdateAsync(dto);

                if (Driver == null)
                {
                    return NotFound();
                }
                return Driver;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/Drivers/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<DriverDto>> SoftDelete(String id)
        {
            var Driver = await _service.InactivateAsync(new DriverId(id));

            if (Driver == null)
            {
                return NotFound();
            }

            return Driver;
        }

        // DELETE: api/Drivers/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<DriverDto>> HardDelete(String id)
        {
            try
            {
                var Driver = await _service.DeleteAsync(new DriverId(id));

                if (Driver == null)
                {
                    return NotFound();
                }

                return Driver;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}