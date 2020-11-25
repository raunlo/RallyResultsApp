import {NgModule} from "@angular/core";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, HttpClientXsrfModule} from "@angular/common/http";
import {RestService} from "./service/rest.service";
import {HttpInterceptorService} from "./service/http.interceptor.service";
import {AuthService} from "./service/auth.service";
import {AuthGuard} from "./auth/auth.guard";
import {BooleanPipe} from "./pipe/boolean.pipe";
import {DriverAndCoDriverNamePipe} from "./pipe/driver-and-co-driver-name.Pipe";
import {Error404Component} from "./error/error.404.component";
import {Error401Component} from "./error/error.401.component";
import {RouterModule} from "@angular/router";

const httpInterceptor = {
  provide: HTTP_INTERCEPTORS,
  useClass: HttpInterceptorService,
  multi: true
}

@NgModule(
  {
    exports: [BooleanPipe, DriverAndCoDriverNamePipe],
    declarations: [BooleanPipe, DriverAndCoDriverNamePipe, Error404Component, Error401Component],
    imports: [HttpClientModule, RouterModule],
    providers: [HttpClientModule, RestService, httpInterceptor, AuthService, AuthGuard],
  }
)
export class SharedModule {
}
