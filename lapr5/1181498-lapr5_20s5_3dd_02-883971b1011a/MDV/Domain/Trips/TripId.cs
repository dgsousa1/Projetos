using System;
using Newtonsoft.Json;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Trips
{
    public class TripId : EntityId
    {

        [JsonConstructor]
        public TripId(Guid value) : base(value)
        {

        }
        public TripId(String value) : base(value)
        {

        }


        override
        protected Object createFromString(String text)
        {
            return text;
        }
        override
        public String AsString()
        {
            Guid obj = Guid.Parse(base.ObjValue.ToString());
            return obj.ToString();
        }
        public Guid AsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}