import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompileResultComponent } from './compile-result.component';

describe('CompileResultComponent', () => {
  let component: CompileResultComponent;
  let fixture: ComponentFixture<CompileResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompileResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompileResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
