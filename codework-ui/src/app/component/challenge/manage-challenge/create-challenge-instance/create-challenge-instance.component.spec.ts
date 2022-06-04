import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateChallengeInstanceComponent } from './create-challenge-instance.component';

describe('CreateChallengeInstanceComponent', () => {
  let component: CreateChallengeInstanceComponent;
  let fixture: ComponentFixture<CreateChallengeInstanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateChallengeInstanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateChallengeInstanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
