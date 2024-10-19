import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FastestPathComponent } from './fastest-path.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('FastestPathComponent', () => {
  let component: FastestPathComponent;
  let fixture: ComponentFixture<FastestPathComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [ FastestPathComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FastestPathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
