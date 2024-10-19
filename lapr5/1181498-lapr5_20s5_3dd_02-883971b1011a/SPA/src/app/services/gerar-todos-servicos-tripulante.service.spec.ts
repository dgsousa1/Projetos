import { TestBed } from '@angular/core/testing';

import { GerarTodosServicosTripulanteService } from './gerar-todos-servicos-tripulante.service';

describe('GerarTodosServicosTripulanteService', () => {
  let service: GerarTodosServicosTripulanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GerarTodosServicosTripulanteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
