import { Component } from '@angular/core';
import { RouterOutlet } from "@angular/router";
import { SideMenu } from '../../components/side-menu/side-menu';

@Component({
  selector: 'app-main-layout',
  imports: [SideMenu, RouterOutlet],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
})
export class MainLayout {}
