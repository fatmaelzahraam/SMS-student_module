import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Violations } from './violation-comp';

describe('ViolationComp', () => {
  let component: Violations;
  let fixture: ComponentFixture<Violations>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Violations]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Violations);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
