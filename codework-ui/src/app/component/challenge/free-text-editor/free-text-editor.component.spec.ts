import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreeTextEditorComponent } from './free-text-editor.component';

describe('FreeTextEditorComponent', () => {
  let component: FreeTextEditorComponent;
  let fixture: ComponentFixture<FreeTextEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FreeTextEditorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FreeTextEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
