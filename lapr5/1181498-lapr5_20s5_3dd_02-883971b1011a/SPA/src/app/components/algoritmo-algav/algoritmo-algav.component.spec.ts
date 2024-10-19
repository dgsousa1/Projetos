import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AlgoritmoAlgavComponent } from './algoritmo-algav.component';
import { FormGroup, ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';



describe('AlgoritmoAlgavComponent', () => {
  let component: AlgoritmoAlgavComponent;
  let fixture: ComponentFixture<AlgoritmoAlgavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [AlgoritmoAlgavComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlgoritmoAlgavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
