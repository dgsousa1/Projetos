using System;
using System.Collections.Generic;
using DDDSample1.Domain.Trips;


namespace DDDSample1.Domain.WorkBlocks
{
    public class WorkBlockMap
    {
        public static WorkBlockDto toDto(WorkBlock wb)
        {
            List<int> tripsList = new List<int>();
            foreach (TripNumber tn in wb.trips)
            {
                tripsList.Add(tn.key);
            }
            return new WorkBlockDto
            {
                Id = wb.Id.AsString(),
                key = wb.key.key,
                startInstant = wb.startInstant.ToString(),
                endInstant = wb.endInstant.ToString(),
                trips = tripsList.ToArray()
            };
        }

        public static WorkBlockInSecondsDto toDtoInSeconds(WorkBlock wb)
        {
            List<int> tripsList = new List<int>();
            foreach (TripNumber tn in wb.trips)
            {
                tripsList.Add(tn.key);
            }
            int startInstantSeconds = wb.startInstant.Hour * 3600 + wb.startInstant.Minute * 60;
            int endInstantSeconds = wb.endInstant.Hour * 3600 + wb.endInstant.Minute * 60;


            return new WorkBlockInSecondsDto
            {
                Id = wb.Id.AsString(),
                key = wb.key.key,
                startInstant = startInstantSeconds,
                endInstant = endInstantSeconds,
                trips = tripsList.ToArray()
            };
        }

        public static WorkBlock toDomain(WorkBlockDto dto)
        {
            List<int> l = new List<int>(dto.trips);
            List<TripNumber> numberList = new List<TripNumber>();
            foreach (int n in l)
            {
                numberList.Add(new TripNumber(n));
            }
            return new WorkBlock(dto.key, dto.startInstant, dto.endInstant, numberList);

        }

    }
}