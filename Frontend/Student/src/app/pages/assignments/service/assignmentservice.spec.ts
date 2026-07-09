import { TestBed } from '@angular/core/testing';

import { Assignmentservice } from './assignmentservice';

describe('Assignmentservice', () => {
  let service: Assignmentservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Assignmentservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
