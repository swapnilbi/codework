import { TestBed } from '@angular/core/testing';

import { LiveChallengeService } from './live-challenge.service';

describe('LiveChallengeService', () => {
  let service: LiveChallengeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LiveChallengeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
