import {NgModule} from "@angular/core";
import {RallyResultsListComponent} from "./rally-results.list.component";
import {ResultsRouting} from "./results.routing";
import {RallyResultsService} from "./rally-results.service";
import {FormsModule} from "@angular/forms";
import { AccordionModule } from 'ngx-bootstrap/accordion';
import {CommonModule} from "@angular/common";
import {StageResultsListComponent} from "./stage-results.list.component";
import {SharedModule} from "../shared/shared.module";
import {NgxSpinnerModule} from "ngx-spinner";
import {PaginationModule} from "ngx-bootstrap/pagination";

@NgModule({
  imports: [ResultsRouting, FormsModule, AccordionModule, CommonModule, SharedModule, NgxSpinnerModule, PaginationModule],
  declarations: [RallyResultsListComponent, StageResultsListComponent],
  bootstrap: [RallyResultsListComponent],
  providers: [RallyResultsService],
  entryComponents: [StageResultsListComponent]
})
export class ResultsModule {}
