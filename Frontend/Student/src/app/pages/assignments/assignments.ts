import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentService } from './service/assignmentservice';


@Component({
  selector: 'app-assignments',
  standalone: true,
  imports: [CommonModule ],
  templateUrl: './assignments.html',
  styleUrls: ['./assignments.css']
})
export class assignments implements OnInit {

  assignments: any[] = [];

  constructor(private service:AssignmentService ) {}

// ngOnInit(): void {

//   this.service.getAssignments().subscribe({

//     next: (res) => {

//       console.log("Response =", res);
//       alert(JSON.stringify(res));

//       this.assignments = res;

//       console.log("Assignments =", this.assignments);

//     },

//     error: (err) => {
//       console.error(err);
//     }

//   });

// }



ngOnInit(): void {

  this.service.getAssignments().subscribe((res: any) => {

    console.log("Response:", res);
    console.log("Is Array:", Array.isArray(res));
    console.log("Length:", res.length);

    this.assignments = res;

    setTimeout(() => {
      console.log("Assignments:", this.assignments);
    }, 1000);

  });

}

  viewAssignment(item: any) {
  console.log('Assignment clicked:', item);
}
}
