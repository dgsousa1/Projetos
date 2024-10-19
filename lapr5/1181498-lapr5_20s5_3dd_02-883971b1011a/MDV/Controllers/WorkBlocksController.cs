using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class WorkBlocksController : ControllerBase
    {
        private readonly WorkBlockService _service;

        public WorkBlocksController(WorkBlockService service)
        {
            _service = service;
        }

        // GET: api/WorkBlock
        [HttpGet]
        public async Task<ActionResult<IEnumerable<WorkBlockDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/WorkBlock
        [HttpGet("Seconds")]
        public async Task<ActionResult<IEnumerable<WorkBlockInSecondsDto>>> GetAllInSeconds()
        {
            return await _service.GetAllInSecondsAsync();
        }

        // GET: api/WorkBlock/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<WorkBlockDto>> GetGetById(String id)
        {
            var WorkBlock = await _service.GetByIdAsync(new WorkBlockId(id));

            if (WorkBlock == null)
            {
                return NotFound();
            }

            return WorkBlock;
        }

        // POST: api/WorkBlocks
        [HttpPost]
        public async Task<ActionResult<WorkBlockDto>> Create(WorkBlockDto dto)
        {
            try
            {
                var WorkBlock = await _service.AddAsync(dto);
                return CreatedAtAction(nameof(GetGetById), new { id = WorkBlock.Id }, WorkBlock);
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

        // PUT: api/WorkBlocks/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<WorkBlockDto>> Update(String id, WorkBlockDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var WorkBlock = await _service.UpdateAsync(dto);

                if (WorkBlock == null)
                {
                    return NotFound();
                }
                return WorkBlock;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/WorkBlocks/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<WorkBlockDto>> SoftDelete(String id)
        {
            var WorkBlock = await _service.InactivateAsync(new WorkBlockId(id));

            if (WorkBlock == null)
            {
                return NotFound();
            }

            return WorkBlock;
        }

        // DELETE: api/WorkBlocks/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<WorkBlockDto>> HardDelete(String id)
        {
            try
            {
                var WorkBlock = await _service.DeleteAsync(new WorkBlockId(id));

                if (WorkBlock == null)
                {
                    return NotFound();
                }

                return WorkBlock;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}