import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorDashboard } from './tutor-dashboard';

describe('TutorDashboard', () => {
  let component: TutorDashboard;
  let fixture: ComponentFixture<TutorDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TutorDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TutorDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
