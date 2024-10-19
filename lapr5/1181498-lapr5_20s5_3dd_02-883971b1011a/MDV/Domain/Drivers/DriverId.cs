using System;
using Newtonsoft.Json;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Drivers
{
    public class DriverId : EntityId
    {
        //9 chars alfanumérico
        [JsonConstructor]
        public DriverId(Guid value) : base(value)
        {

        }

        public DriverId(String value) : base(value)
        {

        }

        override
        protected Object createFromString(String text)
        {
            return new Guid(text);
        }
        override
       public String AsString()
        {
            Guid obj = (Guid)base.ObjValue;
            return obj.ToString();
        }

        public Guid AsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}