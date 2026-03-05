import { Component } from '@angular/core';
import { HeaderPrincipal } from "../../../components/header-principal/header-principal";
import { Hero } from "../../../components/Hero/Hero";


@Component({
  selector: 'app-home-page',
  imports: [HeaderPrincipal, Hero],
  templateUrl: './home-page.html',
})
export class HomePage { }
