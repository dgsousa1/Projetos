import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceDutyComponent } from './service-duty.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { FlashMessagesModule } from 'angular2-flash-messages';


describe('ServiceDutyComponent', () => {
  let component: ServiceDutyComponent;
  let fixture: ComponentFixture<ServiceDutyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule, RouterTestingModule, FlashMessagesModule.forRoot()],
      declarations: [ ServiceDutyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceDutyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
