import {NgModule} from "@angular/core";
import {RallyRoutingModule} from "./rally.routing.module";
import {RallyTableComponent} from "./rally/rally.table.component";
import {RallyDetailComponent} from "./rally/rally.detail.component";
import {RallyStageTableComponent} from "./stage/rally-stage.table.component";
import {RallyStageDetailComponent} from "./stage/rally-stage.detail.component";
import {CompetitorTableComponent} from "./competitor/competitor.table.component";
import {CompetitorDetailComponent} from "./competitor/competitor.detail.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {RallyService} from "../shared/service/rally.service";
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {BsModalService} from "ngx-bootstrap/modal";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {TooltipModule} from "ngx-bootstrap/tooltip";
import {PaginationModule} from "ngx-bootstrap/pagination";
import {RallyStageService} from "../shared/service/rally-stage.service";
import {StageResultsTableComponent} from "./results/stage-results.table.component";
import {NgxSpinnerModule} from "ngx-spinner";
import {CompetitorService} from "../shared/service/competitor.service";
import {TypeaheadModule} from "ngx-bootstrap/typeahead";
import {StageResultsService} from "../shared/service/stage-results.service";
import {StageResultsDetailsComponent} from "./results/stage-results.details.component";
import {AlertModule} from "ngx-bootstrap/alert";

@NgModule({
  declarations: [
    RallyTableComponent,
    RallyDetailComponent,
    RallyStageTableComponent,
    RallyStageDetailComponent,
    CompetitorTableComponent,
    CompetitorDetailComponent,
    StageResultsTableComponent,
    StageResultsDetailsComponent
  ],
  imports: [RallyRoutingModule,
    ReactiveFormsModule,
    CommonModule,
    BsDatepickerModule,
    FontAwesomeModule,
    TooltipModule,
    PaginationModule.forRoot(),
    FormsModule,
    NgxSpinnerModule,
    TypeaheadModule.forRoot(), AlertModule],
  entryComponents: [RallyDetailComponent, RallyStageDetailComponent, CompetitorDetailComponent],
  providers: [RallyService, BsModalService, RallyStageService, CompetitorService, StageResultsService],
  bootstrap: [RallyTableComponent]

})
export class RallyModule {

}
