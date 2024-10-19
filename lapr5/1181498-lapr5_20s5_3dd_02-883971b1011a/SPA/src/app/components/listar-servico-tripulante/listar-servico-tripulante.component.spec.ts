import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarServicoTripulanteComponent } from './listar-servico-tripulante.component';

describe('ListarServicoTripulanteComponent', () => {
  let component: ListarServicoTripulanteComponent;
  let fixture: ComponentFixture<ListarServicoTripulanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarServicoTripulanteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarServicoTripulanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
