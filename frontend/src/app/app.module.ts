import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MDBRootModule} from "angular-bootstrap-md";
import {LayoutModule} from "./layout/layout.module";
import {ApiKeysModule} from "./api-keys/api-keys.module";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {SharedModule} from "./shared/shared.module";
import {RallyModule} from "./rally/rally.module";
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {registerLocaleData} from "@angular/common";

import localeEt from "../../node_modules/@angular/common/locales/et"
import {AppConfig} from "./app.config";
import {CompareModule} from "./compare/compare.module";
import {ResultsModule} from "./results/results.module";

registerLocaleData(localeEt)


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MDBRootModule,
    LayoutModule,
    ApiKeysModule,
    SharedModule,
    NgbModule,
    RallyModule,
    FontAwesomeModule,
    BsDatepickerModule.forRoot(),
    BrowserAnimationsModule,
    CompareModule,
    ResultsModule],
  exports: [
    AppComponent,
  ],
  bootstrap: [AppComponent],
  providers: [{provide: LOCALE_ID, useValue: 'et'}, AppConfig]
})
export class AppModule {
}
