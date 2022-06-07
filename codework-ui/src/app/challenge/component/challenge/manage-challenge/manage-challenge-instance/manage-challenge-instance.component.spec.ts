import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageChallengeInstanceComponent } from './manage-challenge-instance.component';

describe('ManageChallengeInstanceComponent', () => {
  let component: ManageChallengeInstanceComponent;
  let fixture: ComponentFixture<ManageChallengeInstanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageChallengeInstanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageChallengeInstanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
