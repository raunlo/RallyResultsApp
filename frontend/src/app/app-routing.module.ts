import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {AuthGuard} from "./shared/auth/auth.guard";
import {Error404Component} from "./shared/error/error.404.component";
import {Error401Component} from "./shared/error/error.401.component";


const routes: Routes = [
  {
    path: 'admin',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    data: {
      title: 'Ralli tulemuste rakendus'
    },
    children: [
      {
        path: 'api-keys',
        loadChildren: './api-keys/api-keys.module#ApiKeysModule'
      },
      {
        path: 'rally',
        loadChildren: './rally/rally.module#RallyModule'
      }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    data: {
      title: 'Ralli tulemuste rakendus'
    },
    children: [
      {
        path: '',
        loadChildren: './results/results.module#ResultsModule'
      },
      {
        path: 'compare',
        loadChildren: './compare/compare.module#CompareModule'
      }
    ]
  },
  {
    path: "error-401",
    component: Error401Component
  },
  {
    path: "**",
    component: Error404Component
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
