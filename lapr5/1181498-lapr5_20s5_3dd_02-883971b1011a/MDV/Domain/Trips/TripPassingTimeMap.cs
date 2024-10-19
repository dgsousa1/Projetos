using System;
using System.Collections.Generic;

namespace DDDSample1.Domain.Trips
{
    public class TripPassingTimeMap
    {
        public static TripPassingTimeDto toDto(TripPassingTime pt)
        {
            return new TripPassingTimeDto
            {
                number = pt.number,
                time = pt.time,
                nodeName = pt.nodeName,
                isUsed = pt.isUsed,
                isReliefPoint = pt.isReliefPoint
            };
        }

        public static TripPassingTime toDomain(TripPassingTimeDto dto)
        {
            return new TripPassingTime(
                                dto.number, dto.time, dto.nodeName, dto.isUsed, dto.isReliefPoint
                            );
        }

    }
}