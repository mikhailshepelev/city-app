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
  searchKeyword: string = '';
  dataLoaded: boolean = true;

  page: number = 1;
  perPage: number = 10;
  total: number = 0;

  constructor(
    private cityService: CityService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.dataLoaded = false;
      this.getListCities();
    });
  }

  getListCities() {
    this.searchKeyword = this.route.snapshot.paramMap.get('keyword')!;

    this.cityService
      // this.page - 1 needed because spring data Page is 0-based,
      // while npx-pagination is 1-based. Same thing vice-versa in processData method
      .getCities(this.searchKeyword, this.page - 1, this.perPage)
      .subscribe((data) => {
        this.processData(data);
        this.dataLoaded = true;
      });
  }

  processData(data: any) {
    this.cities = data.content;
    this.page = data.number + 1;
    this.perPage = data.size;
    this.total = data.totalElements;
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.getListCities();
  }

  onTableSizeChange(event: any): void {
    this.perPage = event.target.value;
    this.page = 1;
    this.getListCities();
  }

  displayDefaultImage(event: ErrorEvent) {
    (event.target as HTMLImageElement).src = 'assets/images/no-image-found.png';
  }
}
