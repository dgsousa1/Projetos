using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Drivers;


namespace DDDSample1.Domain.Users
{
    public interface IUserService
    {
        Task<List<UserDto>> GetAllAsync();
        Task<UserDto> GetByIdAsync(UserId id);
        Task<UserDto> GetByNameAsync(string name);
        Task<UserDto> AddAsync(UserDto dt);
        Task<UserDto> DeleteByUsername(String username);
        Task<UserDto> UpdateAsync(UserDto dt);
        Task<UserDto> InactivateAsync(UserId id);
        Task<UserDto> DeleteAsync(UserId id);
    }
}
