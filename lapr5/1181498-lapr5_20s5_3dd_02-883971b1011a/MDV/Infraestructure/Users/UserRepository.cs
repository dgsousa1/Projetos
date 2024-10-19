using DDDSample1.Domain.Users;
using DDDSample1.Infrastructure.Shared;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System;

namespace DDDSample1.Infrastructure.Users
{
    public class UserRepository : BaseRepository<User, UserId>, IUserRepository
    {

        private readonly DbSet<User> _objs;
        public UserRepository(DDDSample1DbContext context) : base(context.Users)
        {
            this._objs = context.Users ?? throw new ArgumentNullException("Users not found");
        }

        public async Task<User> GetByUsername(string username)
        {
            return await this._objs.Where(x => username.Equals(x.username)).FirstOrDefaultAsync();
        }
    }
}
