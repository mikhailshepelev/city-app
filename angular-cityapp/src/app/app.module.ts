import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { CitiesListComponent } from './components/cities-list/cities-list.component';
import { HttpClientModule } from '@angular/common/http';
import { CityService } from './services/city.service';
import { SearchComponent } from './components/search/search.component';
import { CityComponent } from './components/city/city.component';
import { CityCardComponent } from './components/city-card/city-card.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CitiesListComponent,
    SearchComponent,
    CityComponent,
    CityCardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxPaginationModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ positionClass: 'toast-bottom-right' }),
  ],
  providers: [CityService],
  bootstrap: [AppComponent],
})
export class AppModule {}
