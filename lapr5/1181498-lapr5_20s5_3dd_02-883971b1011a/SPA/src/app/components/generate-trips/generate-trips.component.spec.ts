import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateTripsComponent } from './generate-trips.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { FlashMessagesModule } from 'angular2-flash-messages';


describe('GenerateTripsComponent', () => {
  let component: GenerateTripsComponent;
  let fixture: ComponentFixture<GenerateTripsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule, RouterTestingModule, FlashMessagesModule.forRoot()],
      declarations: [ GenerateTripsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateTripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
