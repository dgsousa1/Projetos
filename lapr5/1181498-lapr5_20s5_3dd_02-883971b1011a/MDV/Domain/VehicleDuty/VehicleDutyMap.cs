using System;
using System.Collections.Generic;
using DDDSample1.Domain.WorkBlocks;
using DDDSample1.Domain.VehicleDuties;


namespace DDDSample1.Domain.VehicleDuties
{
    public class VehicleDutyMap
    {
        public static VehicleDutyDto toDto(VehicleDuty vd)
        {
            List<String> wbDtoList = new List<String>();
            foreach (WorkBlockKey wb in vd.workBlocks)
            {
                wbDtoList.Add(wb.key);
            }

            return new VehicleDutyDto
            {
                Id = vd.Id.AsString(),
                code = vd.code,
                name = vd.name,
                workBlocks = wbDtoList.ToArray(),
                duration = vd.duration,
                validDate = vd.validDate.ToString(),
                color = vd.color,
                Active = vd.Active
            };

        }

        public static VehicleDuty toDomain(VehicleDutyDto dto)
        {
            List<String> wbList = new List<String>(dto.workBlocks);
            List<WorkBlockKey> l = new List<WorkBlockKey>();

            foreach (String s in wbList)
            {
                l.Add(new WorkBlockKey(s));
            }

            return new VehicleDuty(
                dto.code, dto.name, l, dto.validDate, dto.color
            );
        }

    }
}