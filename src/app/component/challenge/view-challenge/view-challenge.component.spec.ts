import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewChallengeComponent } from './view-challenge.component';

describe('ViewChallengeComponent', () => {
  let component: ViewChallengeComponent;
  let fixture: ComponentFixture<ViewChallengeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewChallengeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewChallengeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
