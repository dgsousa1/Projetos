using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.VehicleDuties;
using DDDSample1.Domain.Trips;
using DDDSample1.Domain.DriverDuties;
using DDDSample1.Domain.WorkBlocks;
using System.Xml.Linq;
using System.Globalization;
using System.Xml.XPath;
using System.IO;

namespace DDDSample1.Domain.Imports
{
    public class ImportService
    {
        private readonly IUnitOfWork _unitOfWork;
        private TripService tripService;
        private VehicleDutyService vdService;
        private DriverDutyService ddService;
        private WorkBlockService wbService;

        XElement world;
        XElement glDocument;
        XElement glDocumentNetwork;
        XElement network;
        XElement nodes;
        XElement glDocumentSchedule;
        XElement schedule;
        XElement trips;
        XElement lines;
        XElement workBlocks;
        XElement vehicleDuties;
        XElement driverDuties;
        XElement driverDutyTypes;
        String errorfile;
        List<String> errorLines;
        public ImportService(IUnitOfWork unitOfWork, TripService tripService, VehicleDutyService vdService, DriverDutyService ddService, WorkBlockService wbService)
        {
            this.errorLines = new List<string>();

            this.errorfile = Settings.xml_filename_error;
            this._unitOfWork = unitOfWork;
            this.tripService = tripService;
            this.vdService = vdService;
            this.ddService = ddService;
            this.wbService = wbService;

            var xml = XDocument.Load(Settings.xml_filename_import);
            IEnumerable<XElement> childList =
            from el in xml.Root.Elements()
            select el;

            this.world = childList.First();
            this.glDocument = world.Elements().First();
            this.glDocumentNetwork = glDocument.Elements().First();
            this.network = glDocumentNetwork.Elements().First();
            this.nodes = network.Elements().ElementAt(3);
            this.lines = network.Elements().ElementAt(1);
            this.glDocumentSchedule = glDocument.Elements().ElementAt(1);
            this.schedule = glDocumentSchedule.Elements().First();
            this.trips = schedule.Elements().First();
            this.workBlocks = schedule.Elements().ElementAt(1);
            this.vehicleDuties = schedule.Elements().ElementAt(2);
            this.driverDuties = schedule.Elements().ElementAt(3);
            this.driverDutyTypes = schedule.Elements().ElementAt(5);

        }
        private bool WriteErrorFile(List<String> errorLines)
        {
            try
            {
                System.IO.File.AppendAllLines(this.errorfile, errorLines);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public async Task<String> ImportFromFile()
        {
            this.errorLines.Add("##########" + DateTime.Now.ToString() + "##########");
            List<TripDto> tripDtoList = readTripsFromFile(this.trips);
            List<WorkBlockDto> workBlockDtos = readWorkBlocksFromFile(this.workBlocks);
            List<VehicleDutyDto> vehicleDutyDtos = readVehicleDutyFromFile(this.vehicleDuties);
            List<DriverDutyDto> driverDutyDtos = readDriverDutyFromFile(this.driverDuties);


            foreach (TripDto dto in tripDtoList)
            {
                try
                {
                    await this.tripService.AddAsync(dto);
                }
                catch (Exception)
                {
                    this.errorLines.Add("Error adding trip to database");
                }
            }
            foreach (WorkBlockDto dto in workBlockDtos)
            {
                try
                {
                    await this.wbService.AddAsync(dto);
                }
                catch (Exception)
                {
                    this.errorLines.Add("Error adding work block " + dto.key + " to database");
                }
            }
            foreach (VehicleDutyDto dto in vehicleDutyDtos)
            {
                try
                {
                    await this.vdService.AddAsync(dto);
                }
                catch (Exception)
                {
                    this.errorLines.Add("Error adding vehicle duty " + dto.code + " to database");
                }
            }
            foreach (DriverDutyDto dto in driverDutyDtos)
            {
                try
                {
                    await this.ddService.AddAsync(dto);
                }
                catch (Exception)
                {
                    this.errorLines.Add("Error adding driver duty " + dto.mecNumber + " to database");
                }
            }

            this.errorLines.Add("###################" + "\n");
            WriteErrorFile(this.errorLines);
            return "Import has finished, check the file: " + Settings.xml_filename_error + " for error information";
        }


        private List<DriverDutyDto> readDriverDutyFromFile(XElement driverDuties)
        {
            List<DriverDutyDto> driverDutyDtos = new List<DriverDutyDto>();
            foreach (XElement driverDuty in driverDuties.Elements())
            {
                String mecNumber;
                try
                {
                    mecNumber = driverDuty.Attribute("key").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Driver duty error: mecNumber");
                    continue;
                }
                String driverName;
                try
                {
                    driverName = driverDuty.Attribute("Name").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Driver duty error: driverName");
                    continue;
                }
                String color;
                try
                {
                    color = driverDuty.Attribute("Color").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Driver duty error: color");
                    continue;
                }
                String validDate = createDateForDriverDuty();
                XElement workBlocksDD = null;
                try
                {
                    workBlocksDD = driverDuty.Elements().First();
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Driver duty error: can't find driver duty work blocks");
                    continue;
                }
                List<String> wbkeys = new List<string>();
                foreach (XElement refDD in workBlocksDD.Elements())
                {
                    String wbKeyFull = null;
                    try
                    {
                        wbKeyFull = refDD.Attribute("key").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Driver duty error: work block key error");
                        continue;
                    }
                    String[] wbKeySep = wbKeyFull.Split(":");
                    wbkeys.Add(wbKeySep[1]);
                }
                String driverDutyType = null;
                try
                {
                    driverDutyType = driverDuty.Attribute("DriverDutyType").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("driver duty error: driver duty type error");
                    continue;
                }
                String type = getElementName(this.driverDutyTypes, driverDutyType);
                driverDutyDtos.Add(
                    new DriverDutyDto
                    {
                        mecNumber = mecNumber,
                        driverName = driverName,
                        color = color,
                        type = type,
                        workBlocks = wbkeys.ToArray(),
                        validDate = validDate
                    }
                );
            }
            return driverDutyDtos;
        }
        private List<VehicleDutyDto> readVehicleDutyFromFile(XElement vehicleDuties)
        {
            List<VehicleDutyDto> vehicleDutyDtos = new List<VehicleDutyDto>();
            foreach (XElement vehicleDuty in vehicleDuties.Elements())
            {
                String code = null;
                try
                {
                    code = vehicleDuty.Attribute("key").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Vehicle duty error: key");
                    continue;
                }
                String name = null;
                try
                {
                    name = vehicleDuty.Attribute("Name").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Vehicle duty error: name");
                    continue;
                }
                String color = null;
                try
                {
                    color = vehicleDuty.Attribute("Color").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Vehicle duty error: color");
                    continue;
                }
                DateTime thisDay = DateTime.Today;
                String validDate = thisDay.ToString("d");
                XElement workBlocksVD = null;
                try
                {
                    workBlocksVD = vehicleDuty.Elements().First();
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("vehicle duty error: can't find vehicle duty work blocks");
                    continue;
                }
                List<String> wbkeys = new List<string>();
                foreach (XElement refVD in workBlocksVD.Elements())
                {
                    String wbKeyFull = null;
                    try
                    {
                        wbKeyFull = refVD.Attribute("key").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("vehicle duty error: work block key");
                        continue;
                    }
                    String[] wbKeySep = wbKeyFull.Split(":");
                    wbkeys.Add(wbKeySep[1]);
                }
                vehicleDutyDtos.Add(
                    new VehicleDutyDto
                    {
                        code = code,
                        name = name,
                        workBlocks = wbkeys.ToArray(),
                        validDate = validDate,
                        color = color
                    }
                );
            }
            return vehicleDutyDtos;
        }
        private List<WorkBlockDto> readWorkBlocksFromFile(XElement workBlocks)
        {
            List<WorkBlockDto> workBlockDtos = new List<WorkBlockDto>();
            foreach (XElement workBlock in workBlocks.Elements())
            {
                String keyFull = null;
                try
                {
                    keyFull = workBlock.Attribute("key").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("work block error : key");
                    continue;
                }
                String[] keySep = keyFull.Split(":");
                String key = keySep[1];
                String startInstant = null;
                try
                {
                    startInstant = createDateForWorkBlock(workBlock.Attribute("StartTime").Value);
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("work block error : startInstant");
                    continue;
                }
                String endInstant = null;
                try
                {
                    endInstant = createDateForWorkBlock(workBlock.Attribute("EndTime").Value);
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("work block error: end Instant");
                    continue;
                }
                XElement wbTrips = null;
                try
                {
                    wbTrips = workBlock.Elements().First();
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("work block error: trips");
                    continue;
                }

                List<int> trips = new List<int>();
                foreach (XElement refT in wbTrips.Elements())
                {
                    String tripKey = null;
                    try
                    {
                        tripKey = refT.Attribute("key").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("work block error: tripKeys");
                        continue;
                    }

                    String[] v = tripKey.Split(":");
                    int tripNumber = Convert.ToInt32(v[1]);
                    trips.Add(tripNumber);
                }
                workBlockDtos.Add(
                    new WorkBlockDto
                    {
                        key = key,
                        startInstant = startInstant,
                        endInstant = endInstant,
                        trips = trips.ToArray()
                    }
                );
            }
            return workBlockDtos;
        }
        private List<TripDto> readTripsFromFile(XElement trips)
        {
            List<TripDto> tripDtos = new List<TripDto>();

            foreach (XElement trip in trips.Elements())
            {
                if (trip.Attribute("Line") != null)
                {
                    String tripKey = null;
                    try
                    {
                        tripKey = trip.Attribute("key").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error: key");
                        continue;
                    }

                    String[] v = tripKey.Split(":");
                    int tripNumber = Convert.ToInt32(v[1]);
                    String orientation = null;
                    try
                    {
                        orientation = trip.Attribute("Orientation").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error orientation");
                        continue;
                    }

                    String line = null;
                    try
                    {
                        line = getElementName(this.lines, trip.Attribute("Line").Value);
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error lines");
                        continue;
                    }
                    String b = null;
                    try
                    {
                        b = trip.Attribute("Path").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error path");
                        continue;
                    }

                    String[] c = b.Split(":");
                    int path = Convert.ToInt32(c[1]);
                    bool isGenerated;
                    try
                    {
                        isGenerated = Convert.ToBoolean(trip.Attribute("IsGenerated").Value);
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error is generated");
                        continue;
                    }
                    XElement passingTimes = null;
                    try
                    {
                        passingTimes = trip.Elements().First();
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Trip error passing times");
                        continue;
                    }

                    List<TripPassingTimeDto> passingTimeDtos = new List<TripPassingTimeDto>();
                    foreach (XElement passingTime in passingTimes.Elements())
                    {
                        String d = null;
                        try
                        {
                            d = trip.Attribute("key").Value;
                        }
                        catch (ArgumentNullException)
                        {
                            this.errorLines.Add("Trip passing time error key");
                            continue;
                        }
                        String[] e = b.Split(":");
                        int number = Convert.ToInt32(c[1]);
                        int time;
                        try
                        {
                            time = Convert.ToInt32(passingTime.Attribute("Time").Value);

                        }
                        catch (ArgumentNullException)
                        {
                            this.errorLines.Add("Trip passing time error time");
                            continue;
                        }
                        bool isUsed;
                        try
                        {
                            isUsed = Convert.ToBoolean(passingTime.Attribute("IsUsed").Value);
                        }
                        catch (ArgumentNullException)
                        {
                            this.errorLines.Add("Trip passing time error isUsed");
                            continue;
                        }
                        bool isReliefPoint;
                        try
                        {
                            isReliefPoint = Convert.ToBoolean(passingTime.Attribute("IsReliefPoint").Value);
                        }
                        catch (ArgumentNullException)
                        {
                            this.errorLines.Add("Trip passing time error isReliefPoint");
                            continue;
                        }
                        String nodeName = null;
                        try
                        {
                            nodeName = getElementName(this.nodes, passingTime.Attribute("Node").Value);
                        }
                        catch (ArgumentNullException)
                        {
                            this.errorLines.Add("Trip passing time error nodeName");
                            continue;
                        }

                        passingTimeDtos.Add(
                            new TripPassingTimeDto
                            {
                                number = number,
                                time = time,
                                nodeName = nodeName,
                                isUsed = isUsed,
                                isReliefPoint = isReliefPoint
                            }
                        );
                    }

                    tripDtos.Add(
                        new TripDto
                        {
                            tripNumber = tripNumber,
                            orientation = orientation,
                            line = line,
                            path = path,
                            isGenerated = isGenerated,
                            passingTimes = passingTimeDtos
                        }
                    );
                }
                else
                {
                    //this.errorLines.Add("Line error");
                }
            }
            return tripDtos;

        }

        private String createDateForDriverDuty()
        {
            DateTime now = DateTime.Now;
            String dayF = now.ToString("u", CultureInfo.CreateSpecificCulture("en-US"));
            String[] dayArray = dayF.Split(" ");
            return dayArray[0];
        }
        private String createDateForWorkBlock(String instant)
        {
            //String hour = createHour(instant);
            DateTime now = DateTime.Today;
            now = now.AddSeconds(Convert.ToDouble(instant));
            String dayF = now.ToString("u", CultureInfo.CreateSpecificCulture("en-US"));
            String[] dayArray = dayF.Split(" ");
            String date = dayArray[0];
            String hour = dayArray[1];
            hour = hour.Replace("Z", "");
            return date + " " + hour;
        }

        private String getElementName(XElement nodes, String key)
        {
            foreach (XElement node in nodes.Elements())
            {
                String nodeKey = null;
                try
                {
                    nodeKey = node.Attribute("key").Value;
                }
                catch (ArgumentNullException)
                {
                    this.errorLines.Add("Descendent element error");
                    continue;
                }
                if (nodeKey.Equals(key))
                {
                    String nodeName = null;
                    try
                    {
                        nodeName = node.Attribute("Name").Value;
                    }
                    catch (ArgumentNullException)
                    {
                        this.errorLines.Add("Error getting element name");
                        continue;
                    }
                    return nodeName;
                }
            }
            return "error";
        }

    }

}