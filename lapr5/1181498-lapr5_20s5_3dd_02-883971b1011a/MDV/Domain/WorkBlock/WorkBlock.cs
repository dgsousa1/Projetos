using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Trips;
using System;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;
using System.Collections.Generic;

namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlock : Entity<WorkBlockId>, IAggregateRoot
    {
        [Required]
        public DateTime startInstant { get; private set; }

        public DateTime endInstant { get; private set; }
        public ICollection<TripNumber> trips { get; private set; }
        public bool Active { get; private set; }

        public WorkBlockKey key { get; private set; }

        private WorkBlock()
        {
            this.Active = true;
        }

        public WorkBlock(string key, string startDate, string endDate, List<TripNumber> tripList)
        {
            this.Id = new WorkBlockId(Guid.NewGuid());
            this.key = new WorkBlockKey(key);
            this.startInstant = Convert.ToDateTime(startDate);

            if (Convert.ToDateTime(endDate) > Convert.ToDateTime(startDate))
                this.endInstant = Convert.ToDateTime(endDate);
            else
                throw new BusinessRuleValidationException("Erro! Data inicial não pode exceder a data final");

            int diferenca = (Convert.ToDateTime(endDate) - Convert.ToDateTime(startDate)).Days;

            if (diferenca > 1)
                throw new BusinessRuleValidationException("Erro! Bloco de trabalho não pode exceder 24 horas");
            else
                this.trips = tripList;
        }

        public int workBlockDurationSeconds()
        {
            double r = (this.endInstant - this.startInstant).TotalSeconds;
            return Convert.ToInt32(Math.Round(r));
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }

    }

}