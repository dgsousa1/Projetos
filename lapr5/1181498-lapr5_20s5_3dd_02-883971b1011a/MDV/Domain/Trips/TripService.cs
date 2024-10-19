using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System;
using DDDSample1.Domain.Shared;
using Newtonsoft.Json;

namespace DDDSample1.Domain.Trips
{
    public class TripService : ITripService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly ITripRepository _repo;

        public TripService()
        {

        }
        public TripService(IUnitOfWork unitOfWork, ITripRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public virtual async Task<List<TripDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();
            List<TripDto> listDto = new List<TripDto>();
            foreach (Trip t in list)
            {
                listDto.Add(TripMap.toDto(t));
            }

            return listDto;
        }

        public virtual async Task<TripDto> GetByIdAsync(TripId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            return TripMap.toDto(t);

        }

        public virtual async Task<TripDto> AddAsync(TripDto dto)
        {
            Trip t = TripMap.toDomain(dto);
            await this._repo.AddAsync(t);

            await this._unitOfWork.CommitAsync();

            return TripMap.toDto(t);
        }

        //========================ADD TRIP ADHOC==========================    
        public virtual async Task<TripDto> AddAdhocAsync(TripAdhocDto tripAdhoc)
        {
            //receber uma tripadhoc
            int tripNumber = tripAdhoc.numeroViagem;
            string orientation = tripAdhoc.orientation;
            string nomeLinha = tripAdhoc.nomeLinha;
            PathDto path = tripAdhoc.path;
            DateTime horaSaida = DateTime.ParseExact(tripAdhoc.horaSaida, "yyyy/MM/dd HH:mm:ss", null);
            int horaInicialEmSegundos = horaSaida.Hour * 3600 + horaSaida.Minute * 60;
            //08:00

            List<TripPassingTime> passingTimes = new List<TripPassingTime>();
            int numberAux = 1;

            foreach (PathNodesDto pathNode in path.pathNodes)
            {
                int duracao = pathNode.duracao;//30
                string inicialNode = pathNode.inicialNode;

                //verificar se o node é reliefPoint ou nao

                passingTimes.Add(new TripPassingTime(
                    (path.key + numberAux), horaInicialEmSegundos, inicialNode, true, true));
                numberAux++;

                horaInicialEmSegundos += duracao; //incrementar a duracao 08:15
            }

            //Adicionar o ultimo passingTime
            passingTimes.Add(new TripPassingTime(
                path.key + numberAux + 1, horaInicialEmSegundos,
                path.pathNodes.Last<PathNodesDto>().finalNode, true, true));

            Trip t = new Trip(tripNumber, orientation,
                nomeLinha, path.key, false, passingTimes);

            await this._repo.AddAsync(t);
            await this._unitOfWork.CommitAsync();

            List<TripPassingTimeDto> l = new List<TripPassingTimeDto>();
            foreach (TripPassingTime pt in t.passingTimes.ToList())
            {

                TripPassingTimeDto tpDto = new TripPassingTimeDto
                {
                    Id = pt.Id.AsString(),
                    number = pt.number,
                    time = pt.time,
                    nodeName = pt.nodeName,
                    isUsed = pt.isUsed,
                    isReliefPoint = pt.isReliefPoint
                };
                l.Add(tpDto);
            }

            TripDto dt = new TripDto
            {
                Id = t.Id.AsString(),
                tripNumber = t.tripNumber.key,
                orientation = t.orientation.orientation,
                line = t.line.lineName,
                path = t.path,
                isGenerated = t.isGenerated,
                passingTimes = l

            };
            return dt;
        }
        //========================ADD TRIP ADHOC==========================    

        public KeyValuePair<int, Trip> createGeneratedTrip(int sumDuracao, PathDto path, string orientation, string nomeLinha)
        {
            Random random = new Random();
            //viagem de ida
            List<TripPassingTime> passingTimes = new List<TripPassingTime>();

            foreach (PathNodesDto pathNode in path.pathNodes)
            {
                string inicialNode = pathNode.inicialNode;
                passingTimes.Add(new TripPassingTime(
                    path.key + sumDuracao, sumDuracao, inicialNode, true, true));
                sumDuracao += pathNode.duracao;
            }
            //adicionar o ultimo passing time
            passingTimes.Add(new TripPassingTime(
                path.key + sumDuracao + 1, sumDuracao, path.pathNodes.Last<PathNodesDto>().finalNode, true, true));

            Trip t = new Trip(random.Next(1, 99999), orientation, nomeLinha, path.key, true, passingTimes);

            return new KeyValuePair<int, Trip>(sumDuracao, t);
        }


        public virtual async Task<List<TripDto>> GenerateTrips(TripGenDto dto)
        {

            List<Trip> allTheTrips = new List<Trip>();

            int frequency = dto.frequencia;
            int horaInicialEmSegundos = int.Parse(dto.horaSaida);
            int nViagens = dto.NViagens;

            PathDto ida = dto.path.First<PathDto>();
            PathDto volta = dto.path.Last<PathDto>();

            int horaAtual = horaInicialEmSegundos;

            Dictionary<Trip, int> ListaP = new Dictionary<Trip, int>();

            int tempoViagem = 0;

            foreach (PathNodesDto pathNode in ida.pathNodes)
            {
                tempoViagem += pathNode.duracao;
            }

            int NParalelos = dto.NParalelos;

            if (NParalelos > 0)
            {
                Console.WriteLine("paralelos:::" + NParalelos);
                //return GenerateParalels(TripGenDto dto);
                while (nViagens != 0)
                {
                    foreach (KeyValuePair<Trip, int> p in ListaP)
                    {
                        // do something with entry.Value or entry.Key
                        if ((p.Value + tempoViagem) >= horaAtual)
                            ListaP.Remove(p.Key);
                    }

                    if ((ListaP.Count < NParalelos) && nViagens > 0)
                    {
                        KeyValuePair<int, Trip> trip = createGeneratedTrip(horaAtual, ida, "Go", dto.nomeLinha);
                        await this._repo.AddAsync(trip.Value);
                        await this._unitOfWork.CommitAsync();
                        allTheTrips.Add(trip.Value);

                        nViagens--;

                        if (nViagens > 0)
                        {
                            KeyValuePair<int, Trip> trip2 = createGeneratedTrip(horaAtual + tempoViagem, volta, "Return", dto.nomeLinha);
                            await this._repo.AddAsync(trip2.Value);
                            await this._unitOfWork.CommitAsync();
                            allTheTrips.Add(trip2.Value);

                            nViagens--;

                            ListaP.Add(trip.Value, horaAtual);
                        }
                    }

                    horaAtual += frequency;

                    Console.WriteLine("XXXXXXXcount::::" + ListaP.Count);
                }


            }
            else
            {
                Console.WriteLine("nao há paralelos:::" + NParalelos);
                int i = 0;

                while (i < nViagens)
                {
                    KeyValuePair<int, Trip> pairTrip = createGeneratedTrip(horaAtual, ida, "Go", dto.nomeLinha);

                    await this._repo.AddAsync(pairTrip.Value);
                    await this._unitOfWork.CommitAsync();
                    allTheTrips.Add(pairTrip.Value);
                    //viagem sucedida
                    i++;
                    if (i < nViagens)
                    {
                        //viagem de volta
                        int sumDuracao = pairTrip.Key;

                        KeyValuePair<int, Trip> pairTrip2 = createGeneratedTrip(sumDuracao, volta, "Return", dto.nomeLinha);

                        await this._repo.AddAsync(pairTrip2.Value);
                        await this._unitOfWork.CommitAsync();
                        allTheTrips.Add(pairTrip2.Value);

                        //viagem sucedida 
                        i++;
                    }
                    horaAtual += frequency;
                }
            }

            List<TripDto> listDto = new List<TripDto>();
            foreach (Trip t in allTheTrips)
            {
                List<TripPassingTimeDto> l = new List<TripPassingTimeDto>();
                foreach (TripPassingTime pt in t.passingTimes)
                {
                    TripPassingTimeDto tpDto = new TripPassingTimeDto
                    {
                        Id = pt.Id.AsString(),
                        number = pt.number,
                        time = pt.time,
                        nodeName = pt.nodeName,
                        isUsed = pt.isUsed,
                        isReliefPoint = pt.isReliefPoint
                    };
                    l.Add(tpDto);
                }

                TripDto dt = new TripDto
                {
                    Id = Convert.ToString(t.Id.Value),
                    tripNumber = t.tripNumber.key,
                    orientation = t.orientation.orientation,
                    line = t.line.lineName,
                    path = t.path,
                    isGenerated = t.isGenerated,
                    passingTimes = l

                };
                listDto.Add(dt);
            }

            return listDto;
        }


        public virtual async Task<TripDto> UpdateAsync(TripDto dto)
        {
            var t = await this._repo.GetByIdAsync(new TripId(dto.Id));

            if (t == null)
                return null;

            // change all field
            t.ChangeOrientation(new Orientation(dto.orientation));

            await this._unitOfWork.CommitAsync();

            return TripMap.toDto(t);

        }

        public virtual async Task<TripDto> InactivateAsync(TripId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            // change all fields
            t.MarkAsInative();

            await this._unitOfWork.CommitAsync();

            return TripMap.toDto(t);

        }

        public virtual async Task<TripDto> DeleteAsync(TripId id)
        {
            var t = await this._repo.GetByIdAsync(id);

            if (t == null)
                return null;

            if (t.Active)
                throw new BusinessRuleValidationException("It is not possible to delete an active Trip.");

            this._repo.Remove(t);
            await this._unitOfWork.CommitAsync();

            return TripMap.toDto(t);
        }
    }
}