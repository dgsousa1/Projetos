using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;


namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TripsController : ControllerBase
    {
        private readonly TripService _service;

        public TripsController(TripService service)
        {
            _service = service;
        }

        // GET: api/Trips
        [HttpGet]
        public virtual async Task<ActionResult<IEnumerable<TripDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Trips/F1
        [HttpGet("{id}")]
        public virtual async Task<ActionResult<TripDto>> GetGetById(String id)
        {
            var trip = await _service.GetByIdAsync(new TripId(id));

            if (trip == null)
            {
                return NotFound();
            }

            return trip;
        }

        // POST: api/Trips
        [HttpPost]
        public virtual async Task<ActionResult<TripDto>> Create(TripDto dto)
        {
            var trip = await _service.AddAsync(dto);

            return CreatedAtAction(nameof(GetGetById), new { id = trip.Id }, trip);
        }

        // POST: api/Trips/Adhoc
        [HttpPost("Adhoc")]
        public virtual async Task<ActionResult<TripDto>> CreateAdhoc(TripAdhocDto dto)
        {
            var tripAdhoc = await _service.AddAdhocAsync(dto);

            return CreatedAtAction(nameof(GetGetById), new { id = tripAdhoc.Id }, tripAdhoc);
        }

        // POST: api/Trips/Gen
        [HttpPost("Gen")]
        public virtual async Task<ActionResult<IEnumerable<TripDto>>> GenerateTrips(TripGenDto dto)
        {
            return await _service.GenerateTrips(dto);
        }

        // PUT: api/Trips/F5
        [HttpPut("{id}")]
        public virtual async Task<ActionResult<TripDto>> Update(String id, TripDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var trip = await _service.UpdateAsync(dto);

                if (trip == null)
                {
                    return NotFound();
                }
                return trip;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/Trips/F5
        [HttpDelete("{id}")]
        public virtual async Task<ActionResult<TripDto>> SoftDelete(String id)
        {
            var trip = await _service.InactivateAsync(new TripId(id));

            if (trip == null)
            {
                return NotFound();
            }

            return trip;
        }

        // DELETE: api/Trips/F5
        [HttpDelete("{id}/hard")]
        public virtual async Task<ActionResult<TripDto>> HardDelete(String id)
        {
            try
            {
                var trip = await _service.DeleteAsync(new TripId(id));

                if (trip == null)
                {
                    return NotFound();
                }

                return trip;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}