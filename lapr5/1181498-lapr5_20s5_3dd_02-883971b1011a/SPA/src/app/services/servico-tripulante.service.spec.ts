import { TestBed } from '@angular/core/testing';

import { ServicoTripulanteService } from './servico-tripulante.service';

describe('ServicoTripulanteService', () => {
  let service: ServicoTripulanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoTripulanteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
