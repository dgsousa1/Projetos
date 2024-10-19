import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarServicoViaturaComponent } from './listar-servico-viatura.component';

describe('ListarServicoViaturaComponent', () => {
  let component: ListarServicoViaturaComponent;
  let fixture: ComponentFixture<ListarServicoViaturaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarServicoViaturaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarServicoViaturaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
