import { TestBed } from '@angular/core/testing';

import { ImportFileService } from './import-file.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('ImportFileService', () => {
  let service: ImportFileService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImportFileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
