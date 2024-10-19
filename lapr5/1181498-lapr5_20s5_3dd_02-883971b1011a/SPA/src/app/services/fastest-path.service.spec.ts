import { TestBed } from '@angular/core/testing';

import { FastestPathService } from './fastest-path.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('FastestPathService', () => {
  let service: FastestPathService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });
  
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FastestPathService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
