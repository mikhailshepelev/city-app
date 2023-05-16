import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CitiesListComponent } from './components/cities-list/cities-list.component';

const routes: Routes = [
  { path: 'search/:keyword', component: CitiesListComponent },
  { path: 'cities', component: CitiesListComponent },
  { path: '', redirectTo: '/cities', pathMatch: 'full' },
  { path: '**', redirectTo: '/cities', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
