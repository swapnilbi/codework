import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkUploadSolutionsComponent } from './bulk-upload-solutions.component';

describe('BulkUploadSolutionsComponent', () => {
  let component: BulkUploadSolutionsComponent;
  let fixture: ComponentFixture<BulkUploadSolutionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BulkUploadSolutionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkUploadSolutionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
