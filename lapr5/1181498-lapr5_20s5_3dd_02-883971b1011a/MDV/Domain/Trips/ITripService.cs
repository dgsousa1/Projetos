using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System;
using DDDSample1.Domain.Shared;
using Newtonsoft.Json;

namespace DDDSample1.Domain.Trips
{
    public interface ITripService
    {
        Task<List<TripDto>> GetAllAsync();
        Task<TripDto> GetByIdAsync(TripId id);
        Task<TripDto> AddAsync(TripDto dto);
        Task<TripDto> AddAdhocAsync(TripAdhocDto tripAdhoc);
        Task<List<TripDto>> GenerateTrips(TripGenDto dto);
        Task<TripDto> UpdateAsync(TripDto dto);
        Task<TripDto> InactivateAsync(TripId id);
        Task<TripDto> DeleteAsync(TripId id);
    }
}
