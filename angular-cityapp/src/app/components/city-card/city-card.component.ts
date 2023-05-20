import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { getUrlFromDataUrl } from 'src/app/utils/image-utils';

@Component({
  selector: 'app-city-card',
  templateUrl: './city-card.component.html',
  styleUrls: ['./city-card.component.css'],
})
export class CityCardComponent {
  @Input() city: any;
  imageUrl: string | ArrayBuffer | null | undefined = '';

  constructor(private router: Router) {}

  ngOnInit() {
    if (this.city?.imageUrl !== null) {
      this.imageUrl = this.city?.imageUrl;
    } else {
      this.imageUrl = getUrlFromDataUrl(this.city?.image);
    }
  }

  editCity(id: number) {
    this.router.navigate(['cities', id]);
  }

  displayDefaultImage(event: ErrorEvent) {
    (event.target as HTMLImageElement).src = 'assets/images/no-image-found.png';
  }
}
