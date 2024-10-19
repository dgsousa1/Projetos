import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkBlockComponent } from './work-block.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { FlashMessagesModule } from 'angular2-flash-messages';


describe('WorkBlockComponent', () => {
  let component: WorkBlockComponent;
  let fixture: ComponentFixture<WorkBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule, RouterTestingModule, FlashMessagesModule.forRoot()],
      declarations: [ WorkBlockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
