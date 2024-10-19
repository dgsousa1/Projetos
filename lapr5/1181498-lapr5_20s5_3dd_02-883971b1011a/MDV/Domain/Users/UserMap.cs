using System;


namespace DDDSample1.Domain.Users
{
    public class UserMap
    {
        public static UserDto toDto(User user)
        {
            return new UserDto
            {
                Id = Convert.ToString(user.Id.Value),
                name = user.name,
                role = user.role,
                username = user.username,
                password = EncryptionHelper.Decrypt(user.password.password),
                telephone = user.telephone.telephone,
                email = user.email.email,
                nif = user.nif.nif
            };
        }

        public static User toDomain(UserDto dto)
        {
            String pass = EncryptionHelper.Encrypt(dto.password);
            return new User(dto.name, dto.role, dto.username, pass, dto.telephone
            , dto.email, dto.nif);
        }

    }
}