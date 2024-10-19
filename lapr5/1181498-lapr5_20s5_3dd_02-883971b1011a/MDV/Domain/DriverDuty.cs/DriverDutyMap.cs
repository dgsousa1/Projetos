using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;

namespace DDDSample1.Domain.DriverDuties
{
    public class DriverDutyMap
    {
        public static DriverDutyDto toDto(DriverDuty driverDuty)
        {
            List<String> wbDtoList = new List<String>();
            foreach (WorkBlockKey wb in driverDuty.workBlocks)
            {
                wbDtoList.Add(wb.key);
            }
            return new DriverDutyDto
            {
                Id = driverDuty.Id.AsString(),
                mecNumber = driverDuty.mecNumber,
                driverName = driverDuty.driverName,
                color = driverDuty.color,
                type = driverDuty.type.type,
                workBlocks = wbDtoList.ToArray(),
                duration = driverDuty.duration,
                validDate = driverDuty.validDate.ToString()
            };
        }

        public static DriverDuty toDomain(DriverDutyDto dto)
        {
            List<String> wbList = new List<String>(dto.workBlocks);
            List<WorkBlockKey> l = new List<WorkBlockKey>();
            foreach (String s in wbList)
            {
                l.Add(new WorkBlockKey(s));
            }
            return new DriverDuty(
                dto.mecNumber, dto.driverName, dto.color, dto.type, l, dto.validDate
            );
        }
    }
}