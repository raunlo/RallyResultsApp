import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {RallyTableComponent} from "./rally/rally.table.component";
import {RallyStageTableComponent} from "./stage/rally-stage.table.component";
import {CompetitorTableComponent} from "./competitor/competitor.table.component";
import {StageResultsTableComponent} from "./results/stage-results.table.component";

const routes: Routes = [
  {
    path: '',
    component: RallyTableComponent
  },
  {
    path: ':rallyId/stages',
    component: RallyStageTableComponent
  },
  {
    path: 'competitor',
    component: CompetitorTableComponent
  },
  {
    path: ':rallyId/stages/:stageId/result',
    component: StageResultsTableComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RallyRoutingModule {

}
