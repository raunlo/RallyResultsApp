import {NgModule} from "@angular/core";
import {CompareComponent} from "./compare.component";
import {CompareRouting} from "./compare.routing";
import {ChartsModule} from "ng2-charts";
import {TabsModule} from "ngx-bootstrap/tabs";
import {CompareCompetitorsOnStage} from "./compare.competitors-on-stage";
import {CompareService} from "./compare.service";
import {FormsModule} from "@angular/forms";
import {TypeaheadModule} from "ngx-bootstrap/typeahead";
import {CommonModule} from "@angular/common";

@NgModule({
    imports: [CompareRouting, ChartsModule, TabsModule, FormsModule, TypeaheadModule, CommonModule],
  declarations: [CompareComponent, CompareCompetitorsOnStage],
  providers: [CompareService],
  bootstrap: [CompareComponent]
})
export class CompareModule {

}
