import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { City } from 'src/app/models/city';
import { CityService } from 'src/app/services/city.service';
import { ToastrService } from 'ngx-toastr';
import { getUrlFromDataUrl } from 'src/app/utils/image-utils';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css'],
})
export class CityComponent {
  id: number = -1;
  city: City | undefined;
  imageUrl: string | ArrayBuffer | null | undefined = '';
  selectedFile: File | undefined;

  constructor(
    private cityService: CityService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.city = new City(-1, '', '', new Uint8Array());
    this.getCityById(this.id);
  }

  getCityById(id: number) {
    if (id != -1) {
      this.cityService.getCityById(id).subscribe((data) => {
        this.city = data;
        if (this.city?.imageUrl !== null) {
          this.imageUrl = this.city?.imageUrl;
        } else {
          this.imageUrl = getUrlFromDataUrl(this.city?.image);
        }
      });
    }
  }

  onSelectFile(event: any) {
    const file = event.target.files[0];
    if (file.type.startsWith('image/')) {
      let reader = new FileReader();
      reader.onloadend = (e) => {
        this.imageUrl = reader.result;
      };
      reader.readAsDataURL(file);
      this.selectedFile = file;
    } else {
      this.toastr.error('Selected file is not an image.');
    }
  }

  saveCity() {
    const form = new FormData();
    if (!!this.selectedFile) {
      const imageBlob = new Blob([this.selectedFile]);
      form.append('image', imageBlob);
    }
    if (this.city?.name) {
      form.append('name', this.city.name);
    }

    this.cityService.updateCity(form, this.id).subscribe((data) => {
      this.toastr.success('City successfully saved!');
      this.city = data;
    });
  }

  navigateToCities() {
    this.router.navigateByUrl('/cities');
  }

  displayDefaultImage(event: ErrorEvent) {
    (event.target as HTMLImageElement).src = 'assets/images/no-image-found.png';
  }
}
