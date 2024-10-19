using System;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class TripMap
    {
        public static TripDto toDto(Trip trip)
        {
            List<TripPassingTimeDto> l = new List<TripPassingTimeDto>();
            foreach (TripPassingTime pt in trip.passingTimes)
            {
                l.Add(TripPassingTimeMap.toDto(pt));
            }

            return new TripDto
            {
                Id = trip.Id.AsString(),
                tripNumber = trip.tripNumber.key,
                orientation = trip.orientation.orientation,
                line = trip.line.lineName,
                path = trip.path,
                isGenerated = trip.isGenerated,
                passingTimes = l
            };
        }

        public static Trip toDomain(TripDto dto)
        {
            List<TripPassingTime> list = new List<TripPassingTime>();
            foreach (TripPassingTimeDto d in dto.passingTimes)
            {
                list.Add(TripPassingTimeMap.toDomain(d));
            }
            return new Trip(dto.tripNumber, dto.orientation,
            dto.line, dto.path, dto.isGenerated, list);
        }

    }
}