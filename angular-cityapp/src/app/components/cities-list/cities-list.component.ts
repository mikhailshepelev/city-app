import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { City } from 'src/app/models/city';
import { CityService } from 'src/app/services/city.service';

@Component({
  selector: 'app-cities-list',
  templateUrl: './cities-list.component.html',
  styleUrls: ['./cities-list.component.css'],
})
export class CitiesListComponent {
  cities: City[] = [];
  searchMode: boolean = false;
  keyword: string = '';
  citiesLoaded: boolean = true;

  constructor(
    private cityService: CityService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.citiesLoaded = false;
      this.handleListCities();
    });
  }

  listCities() {
    this.cityService.getCitiesList().subscribe((data) => {
      console.log(data);
      this.cities = data;
      this.citiesLoaded = true;
    });
  }

  handleListCities() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    const keyword: string = this.route.snapshot.paramMap.get('keyword')!;

    if (this.searchMode) {
      console.log('Search active ');
      this.searchCities(keyword);
    } else {
      console.log('Search inactive');
      this.listCities();
    }
  }

  searchCities(name: string) {
    this.cityService.searchCities(name).subscribe((data) => {
      console.log(data);
      this.cities = data;
      this.citiesLoaded = true;
    });
  }

  displayDefaultImage(event: ErrorEvent) {
    (event.target as HTMLImageElement).src = 'assets/images/no-image-found.png';
  }
}
