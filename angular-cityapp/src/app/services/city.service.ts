import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
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
}

interface GetResponse {
  cities: City[];
}
