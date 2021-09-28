import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeWidgetComponent } from './challenge-widget.component';

describe('ChallengeWidgetComponent', () => {
  let component: ChallengeWidgetComponent;
  let fixture: ComponentFixture<ChallengeWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChallengeWidgetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChallengeWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
