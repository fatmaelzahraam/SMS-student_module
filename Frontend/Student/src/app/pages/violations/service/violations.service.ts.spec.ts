import { TestBed } from '@angular/core/testing';

import { ViolationsServiceTs } from './violations.service.ts';

describe('ViolationsServiceTs', () => {
  let service: ViolationsServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViolationsServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
