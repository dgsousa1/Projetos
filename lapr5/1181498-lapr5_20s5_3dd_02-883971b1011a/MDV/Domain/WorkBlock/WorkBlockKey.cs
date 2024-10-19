using System;
using DDDSample1.Domain.Shared;
using System.Collections.Generic;

namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockKey : ValueObject
    {

        public string key { get; set; }

        private WorkBlockKey()
        {

        }

        public WorkBlockKey(string value)
        {
            this.key = value;
        }

        protected override IEnumerable<object> GetEqualityComponents()
        {
            yield return key;
        }
    }
}