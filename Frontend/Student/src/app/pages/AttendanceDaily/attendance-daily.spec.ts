import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceDaily } from './attendance-daily';

describe('AttendanceDaily', () => {
  let component: AttendanceDaily;
  let fixture: ComponentFixture<AttendanceDaily>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendanceDaily]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttendanceDaily);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
