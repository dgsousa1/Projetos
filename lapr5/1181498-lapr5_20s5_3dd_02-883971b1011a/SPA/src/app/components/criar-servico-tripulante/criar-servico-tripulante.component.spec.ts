import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarServicoTripulanteComponent } from './criar-servico-tripulante.component';

describe('CriarServicoTripulanteComponent', () => {
  let component: CriarServicoTripulanteComponent;
  let fixture: ComponentFixture<CriarServicoTripulanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarServicoTripulanteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarServicoTripulanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
