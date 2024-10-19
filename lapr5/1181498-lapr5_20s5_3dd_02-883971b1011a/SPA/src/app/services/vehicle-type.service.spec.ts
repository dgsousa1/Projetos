import { TestBed } from '@angular/core/testing';

import { VehicleTypeService } from './vehicle-type.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('VehicleTypeService', () => {
  let service: VehicleTypeService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehicleTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
