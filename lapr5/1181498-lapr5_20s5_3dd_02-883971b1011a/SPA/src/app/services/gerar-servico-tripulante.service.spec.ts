import { TestBed } from '@angular/core/testing';

import { GerarServicoTripulanteService } from './gerar-servico-tripulante.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('GerarServicoTripulanteService', () => {
  let service: GerarServicoTripulanteService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GerarServicoTripulanteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
