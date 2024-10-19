using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Imports;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ImportsController : ControllerBase
    {
        private readonly ImportService _service;

        public ImportsController(ImportService service)
        {
            _service = service;
        }

        // GET: api/DriverDuties
        [HttpGet]
        public async Task<ActionResult<String>> GetAll()
        {
            return await _service.ImportFromFile();
        }

    }
}