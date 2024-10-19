using System;
using Newtonsoft.Json;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockId : EntityId
    {
        [JsonConstructor]
        public WorkBlockId(Guid value) : base(value)
        {

        }

        public WorkBlockId(String value) : base(value)
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
            Guid obj = Guid.Parse(base.ObjValue.ToString());

            return obj.ToString();
        }

        public Guid AsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}