import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ApiKeyTableComponent} from "./api-key.table.component";

const routes: Routes = [
  {
    path : '',
    component : ApiKeyTableComponent
  }
]

@NgModule({
  imports : [RouterModule.forChild(routes)],
  exports : [RouterModule]
})
export class ApiKeysRoutingModule {

}
