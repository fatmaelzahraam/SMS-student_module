import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceNav } from './attendance-nav';

describe('AttendanceNav', () => {
  let component: AttendanceNav;
  let fixture: ComponentFixture<AttendanceNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendanceNav]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttendanceNav);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
