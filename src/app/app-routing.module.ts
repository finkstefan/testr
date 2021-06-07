import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './components/core/about/about.component';
import { AuthorComponent } from './components/core/author/author.component';
import { HomeComponent } from './components/core/home/home.component';
import { DepartmanComponent } from './components/departman/departman.component';
import { FakultetComponent } from './components/fakultet/fakultet.component';
import { StatusComponent } from './components/status/status.component';
import { StudentComponent } from './components/student/student.component';


const routes: Routes = [
  {path:'fakultet', component: FakultetComponent},
  {path:'student', component: StudentComponent},
  {path:'departman', component: DepartmanComponent},
  {path:'status', component: StatusComponent},
  {path:'home', component: HomeComponent},
  {path:'about', component: AboutComponent},
  {path:'author', component: AuthorComponent},
  {path:'', redirectTo: 'home', pathMatch: 'full'}, //pathMatch za izbegavanje beskonacne petlje
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
