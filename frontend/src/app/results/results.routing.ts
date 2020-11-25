import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {RallyResultsListComponent} from "./rally-results.list.component";

const routes: Routes = [{
  path: "",
  component: RallyResultsListComponent
}]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResultsRouting {

}
