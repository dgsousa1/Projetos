using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Drivers;


namespace DDDSample1.Domain.Users
{
    public class UserService : IUserService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IUserRepository _repo;

        public UserService(){
            
        }

        public UserService(IUnitOfWork unitOfWork, IUserRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public virtual async Task<List<UserDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();
            List<UserDto> listDto = new List<UserDto>();
            foreach (User t in list)
            {
                listDto.Add(UserMap.toDto(t));
            }

            return listDto;
        }

        public virtual async Task<UserDto> GetByIdAsync(UserId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> GetByNameAsync(string name)
        {
            var t = await this._repo.GetByUsername(name);

            if (t == null)
                return null;

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> AddAsync(UserDto dt)
        {
            User t = UserMap.toDomain(dt);

            await this._repo.AddAsync(t);

            await this._unitOfWork.CommitAsync();

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> DeleteByUsername(String username)
        {
            var t = await this._repo.GetByUsername(username);

            if (t == null)
                return null;

            if (t.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active User.");

            this._repo.Remove(t);
            await this._unitOfWork.CommitAsync();

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> UpdateAsync(UserDto dt)
        {
            var t = await this._repo.GetByIdAsync(new UserId(dt.Id));

            if (t == null)
                return null;

            t.ChangePassword(EncryptionHelper.Encrypt(dt.password));

            await this._unitOfWork.CommitAsync();

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> InactivateAsync(UserId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            // change all fields
            t.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            UserDto dto = UserMap.toDto(t);

            return dto;
        }

        public virtual async Task<UserDto> DeleteAsync(UserId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            if (t.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active User.");

            this._repo.Remove(t);
            await this._unitOfWork.CommitAsync();

            UserDto dto = UserMap.toDto(t);

            return dto;
        }
    }
}