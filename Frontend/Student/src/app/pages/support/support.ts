import { Component } from '@angular/core';
import { Complaints } from '../complaints/complaints';
import { Violations } from '../violations/violation-comp';

@Component({
  selector: 'app-support',
  imports: [Complaints],
  templateUrl: './support.html',
  styleUrl: './support.css',
})
export class Support {

}
