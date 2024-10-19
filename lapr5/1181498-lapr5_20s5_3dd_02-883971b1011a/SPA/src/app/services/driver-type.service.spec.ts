import { TestBed } from '@angular/core/testing';

import { DriverTypeService } from './driver-type.service';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DriverTypeModel } from '../models/driver-type.model';
import { environment } from 'src/environments/environment';

describe('DriverTypeService', () => {
  let service: DriverTypeService;
  let driverType: DriverTypeModel;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DriverTypeService]
    });
    service = TestBed.inject(DriverTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  it('getDriverTypes() should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    driverType = {
      driverTypeId: "d1a36539-b1a4-4a6f-a217-c427fce7337c",
      name: "TripulanteA",
      description: "Descrição do tripulante A"
    };

    //Act
    service.getDriverTypes().subscribe((dt)=>{
      //Assert-1
      expect(dt).toEqual([]);
 
    });
     
    //Assert -2
    const req = httpMock.expectOne(environment.apiUrl + '/api/driverTypes/all');
 
    //Assert -3
    expect(req.request.method).toEqual("GET");

    //Assert -4
    req.flush(driverType);

    //Assert -5
    httpMock.verify();



  })


});