import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportFileMdvComponent } from './import-file-mdv.component';

describe('ImportFileMdvComponent', () => {
  let component: ImportFileMdvComponent;
  let fixture: ComponentFixture<ImportFileMdvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImportFileMdvComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportFileMdvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
