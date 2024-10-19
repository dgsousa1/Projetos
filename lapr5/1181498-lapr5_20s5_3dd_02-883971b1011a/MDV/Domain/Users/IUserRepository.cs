using DDDSample1.Domain.Shared;
using System.Threading.Tasks;



namespace DDDSample1.Domain.Users
{
    public interface IUserRepository : IRepository<User, UserId>
    {
        Task<User> GetByUsername(string username);

    }
}