import { Component } from '@angular/core';
import { Cards } from '../cards/cards';
import { Header } from '../header/header';
import { Footer } from '../footer/footer';
import { SideMenu } from '../side-menu/side-menu';

@Component({
  selector: 'app-courses',
  imports: [Cards , Header , Footer , SideMenu],
  templateUrl: './courses.html',
  styleUrl: './courses.css',
})
export class Courses {

}
