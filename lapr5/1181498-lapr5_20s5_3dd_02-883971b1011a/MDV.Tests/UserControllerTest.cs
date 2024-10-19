using System;
using Moq;
using DDDSample1.Domain.Users;
using DDDSample1.Controllers;
using System.Collections.Generic;
using NUnit.Framework;
using System.Threading.Tasks;


namespace UserControllerTest
{
    public class UserControllerTest
    {
        private UsersController controller;
        private Mock<UserService> serviceMock;

        [Test]
        public void GetAllUsersTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            UserId bId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            string[] workBlocksB = { "2" };
            List<UserDto> list = new List<UserDto>();
            UserDto a = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };

            UserDto b = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };
            list.Add(a);
            list.Add(b);
            serviceMock.Setup(x => x.GetAllAsync()).Returns(Task.FromResult(list));
            Assert.AreEqual(list, controller.GetAll().Result.Value);

        }

        [Test]
        public void GetByIdTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            UserDto expected = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };

            serviceMock.Setup(x => x.GetByIdAsync(It.IsAny<UserId>())).Returns(Task.FromResult(expected));
            string id = aId.AsString();
            UserDto result = controller.GetGetById(id).Result.Value;
            Assert.AreEqual(expected, result);

        }

        [Test]
        public void UpdateTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            UserDto expected = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };
            UserDto sent = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };




            serviceMock.Setup(x => x.UpdateAsync(It.IsAny<UserDto>())).Returns(Task.FromResult(expected));
            UserDto result = controller.Update(aId.AsString(), sent).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void SoftDeleteTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            UserDto expected = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };
            UserDto sent = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };

            serviceMock.Setup(x => x.InactivateAsync(It.IsAny<UserId>())).Returns(Task.FromResult(expected));
            UserDto result = controller.SoftDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void HardDeleteTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            UserDto expected = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };
            UserDto sent = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };

            serviceMock.Setup(x => x.DeleteAsync(It.IsAny<UserId>())).Returns(Task.FromResult(expected));
            UserDto result = controller.HardDelete(aId.AsString()).Result.Value;

            Assert.AreEqual(expected, result);
        }


        [Test]
        public void DeleteFromUsernameTest()
        {
            this.serviceMock = new Mock<UserService>();
            this.controller = new UsersController(serviceMock.Object);
            UserId aId = new UserId(Guid.NewGuid());
            string[] workBlocksA = { "1" };
            UserDto expected = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };
            UserDto sent = new UserDto
            {
                Id = aId.AsString(),
                name = "Administrador",
                role = "administrador",
                username = "Admin1",
                password = "password",
                telephone = 123456789,
                email = "admin@gmail.com",
                nif = 123457779
            };

            serviceMock.Setup(x => x.DeleteByUsername(It.IsAny<String>())).Returns(Task.FromResult(expected));
            UserDto result = controller.DeleteByUsername(sent.username).Result.Value;

            Assert.AreEqual(expected, result);
        }

    }
}
