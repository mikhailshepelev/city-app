import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { City } from '../models/city';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private baseUrl = 'http://localhost:8080/cities';

  constructor(private http: HttpClient) {}

  getCitiesList() {
    return this.http.get<City[]>(this.baseUrl);
  }

  searchCities(keyword: string) {
    return this.http.get<City[]>(`${this.baseUrl}/search?name=${keyword}`);
  }

  getCities(keyword: string, page: number, perPage: number) {
    const searchUrl =
      `http://localhost:8080/cities-paginated?keyword=${keyword}` +
      `&page=${page}&perPage=${perPage}`;
    return this.http.get<any>(searchUrl);
  }
}
