import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeInstructionComponent } from './challenge-instruction.component';

describe('ChallengeInstructionComponent', () => {
  let component: ChallengeInstructionComponent;
  let fixture: ComponentFixture<ChallengeInstructionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChallengeInstructionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChallengeInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
