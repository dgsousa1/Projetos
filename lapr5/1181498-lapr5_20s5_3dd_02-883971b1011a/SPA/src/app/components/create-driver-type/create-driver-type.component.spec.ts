import { async, ComponentFixture, TestBed, inject, tick, fakeAsync } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { CreateDriverTypeComponent } from './create-driver-type.component';
import { DriverTypeService } from '../../services/driver-type.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


describe('CreateDriverTypeComponent', () => {
  let service: DriverTypeService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  let component: CreateDriverTypeComponent;
  let fixture: ComponentFixture<CreateDriverTypeComponent>;
  let de: DebugElement;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [CreateDriverTypeComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CreateDriverTypeComponent]
    })
    fixture = TestBed.createComponent(CreateDriverTypeComponent);
    component = fixture.componentInstance;
    de = fixture.debugElement;
    service = TestBed.inject(DriverTypeService);
    httpMock = TestBed.get(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have H2 tag of Criar Tipo de Tripulante', () => {
    expect(de.query(By.css('h2')).nativeElement.innerText).toBe('Criar Tipo de Tripulante');
  });


});
