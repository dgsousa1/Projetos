import { TestBed } from '@angular/core/testing';

import { LineService } from './line.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('LineService', () => {
  let service: LineService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LineService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
