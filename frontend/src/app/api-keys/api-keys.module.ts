import {NgModule} from "@angular/core";
import {ApiKeyTableComponent} from "./api-key.table.component";
import {ApiKeysRoutingModule} from "./api-keys.routing.module";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {ApiKeysService} from "./api.keys.service";
import {AlertModule} from "ngx-bootstrap/alert";
import {ApiKeyDetailComponent} from "./api-key.detail.component";
import { ClipboardModule } from 'ngx-clipboard';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {TooltipModule} from "ngx-bootstrap/tooltip";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [
    ApiKeyTableComponent,
    ApiKeyDetailComponent,
  ],
  imports: [
    ApiKeysRoutingModule,
    CommonModule,
    ReactiveFormsModule,
    AlertModule,
    ClipboardModule,
    FontAwesomeModule,
    TooltipModule,
    SharedModule
  ],
  bootstrap: [ApiKeyTableComponent],
  providers: [ApiKeysService],
  entryComponents: [ApiKeyDetailComponent]
})
export class ApiKeysModule {
}
