import { TestBed } from '@angular/core/testing';

import { WorkBlockService } from './work-block.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('WorkBlockService', () => {
  let service: WorkBlockService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkBlockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
