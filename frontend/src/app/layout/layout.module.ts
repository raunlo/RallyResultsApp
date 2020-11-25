import {NgModule} from "@angular/core";
import {LayoutComponent} from "./layout.component";
import {RouterModule} from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CommonModule} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import {LoginComponent} from "./login.component";
import {ReactiveFormsModule} from "@angular/forms";
import {TooltipModule} from "ngx-bootstrap/tooltip";

@NgModule({
  imports: [
    BsDropdownModule,
    RouterModule,
    NgbModule,
    CommonModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    TooltipModule
  ],
  declarations: [LayoutComponent, LoginComponent],
  entryComponents: [LoginComponent]
})
export class LayoutModule {
}
