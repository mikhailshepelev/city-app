import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private baseUrl = 'http://localhost:8080/cities';

  constructor(private http: HttpClient) {}

  getCities(keyword: string, page: number, perPage: number) {
    const searchUrl = `${this.baseUrl}?keyword=${keyword}&page=${page}&perPage=${perPage}`;
    return this.http.get<any>(searchUrl);
  }

  getCityById(id: number) {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  updateCity(data: FormData, id: number) {
    return this.http.post<any>(`${this.baseUrl}/${id}`, data);
  }
}
