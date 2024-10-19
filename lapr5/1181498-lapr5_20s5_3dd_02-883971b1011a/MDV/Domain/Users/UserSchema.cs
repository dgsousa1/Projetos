using System;


namespace DDDSample1.Domain.Users
{
    public class UserSchema
    {
        public String Id { get; set; }
        public string name { get; set; }

        public string username { get; set; }
        public string password { get; set; }
        public int telephone { get; set; }
        public string email { get; set; }
        public int nif { get; set; }
        public bool Active { get; set; }
    }
}