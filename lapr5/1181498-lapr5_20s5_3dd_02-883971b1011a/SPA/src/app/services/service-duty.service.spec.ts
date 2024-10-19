import { TestBed } from '@angular/core/testing';

import { ServiceDutyService } from './service-duty.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('ServiceDutyService', () => {
  let service: ServiceDutyService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });
  
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceDutyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
