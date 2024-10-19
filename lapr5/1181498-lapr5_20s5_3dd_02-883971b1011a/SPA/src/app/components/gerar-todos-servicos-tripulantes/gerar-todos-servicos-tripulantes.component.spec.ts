import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerarTodosServicosTripulantesComponent } from './gerar-todos-servicos-tripulantes.component';

describe('GerarTodosServicosTripulantesComponent', () => {
  let component: GerarTodosServicosTripulantesComponent;
  let fixture: ComponentFixture<GerarTodosServicosTripulantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GerarTodosServicosTripulantesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GerarTodosServicosTripulantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
