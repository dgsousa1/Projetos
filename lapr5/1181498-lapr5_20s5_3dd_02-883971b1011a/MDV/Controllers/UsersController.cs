using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Users;
using Microsoft.AspNetCore.Authorization;
using System.Linq;


namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly UserService _service;

        public UsersController(UserService service)
        {
            _service = service;
        }

        [HttpGet]
        [Route("auth")]
        [Authorize]
        public string Authenticated() => String.Format("Authenticated!" + User.Identity.Name);

        [HttpPost]
        [Route("login")]
        [AllowAnonymous]
        public async Task<ActionResult<dynamic>> Authenticate([FromBody] UserDto model)
        {
            var user = await _service.GetByNameAsync(model.username);
            if (!user.password.Equals(model.password))
            {
                return BadRequest();
            }
            var token = TokenService.GenerateToken(user);
            user.password = "";
            return new
            {
                user = user,
                token = token
            };
        }

        // GET: api/Users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Users/F1
        [HttpGet("{id}")]
        public async Task<ActionResult<UserDto>> GetGetById(String id)
        {
            var User = await _service.GetByIdAsync(new UserId(id));

            if (User == null)
            {
                return NotFound();
            }

            return User;
        }

        // POST: api/Users
        [HttpPost]
        public async Task<ActionResult<UserDto>> Create(UserDto dto)
        {
            try
            {
                var User = await _service.AddAsync(dto);
                return CreatedAtAction(nameof(GetGetById), new { id = User.Id }, User);
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


        // PUT: api/Users/F5
        [HttpPut("{id}")]
        public async Task<ActionResult<UserDto>> Update(String id, UserDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var User = await _service.UpdateAsync(dto);

                if (User == null)
                {
                    return NotFound();
                }
                return User;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // Inactivate: api/Users/F5
        [HttpDelete("{id}")]
        public async Task<ActionResult<UserDto>> SoftDelete(String id)
        {
            var User = await _service.InactivateAsync(new UserId(id));

            if (User == null)
            {
                return NotFound();
            }

            return User;
        }

        // DELETE: api/Users/F5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<UserDto>> HardDelete(String id)
        {
            try
            {
                var User = await _service.DeleteAsync(new UserId(id));

                if (User == null)
                {
                    return NotFound();
                }

                return User;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        [HttpDelete("{username}/delete")]
        public async Task<ActionResult<UserDto>> DeleteByUsername(String username)
        {
            try
            {
                var User = await _service.DeleteByUsername(username);

                if (User == null)
                {
                    return NotFound();
                }

                return User;
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}