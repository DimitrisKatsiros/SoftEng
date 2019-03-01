import { Component, OnInit } from '@angular/core';
import { Shops } from '../shops';
import { ApiShopsService } from '../api-shops.service'; 

@Component({
  selector: 'app-shops-search',
  templateUrl: './shops-search.component.html',
  styleUrls: ['./shops-search.component.css']
})
export class ShopsSearchComponent implements OnInit {
  shops: Shops[];

  constructor(private apis: ApiShopsService) { }

  ngOnInit() {
    this.apis.getShops().subscribe((data: Shops[]) => {this.shops = data ;});
  }

}
