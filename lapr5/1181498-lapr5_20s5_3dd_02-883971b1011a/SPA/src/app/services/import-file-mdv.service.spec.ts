import { TestBed } from '@angular/core/testing';

import { ImportFileMdvService } from './import-file-mdv.service';

describe('ImportFileMdvService', () => {
  let service: ImportFileMdvService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImportFileMdvService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
