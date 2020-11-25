import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {CompareComponent} from "./compare.component";

const routes: Routes = [{
  path: "",
  component: CompareComponent
}]
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompareRouting{}
