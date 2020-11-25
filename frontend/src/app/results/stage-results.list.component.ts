import {Component, Input, OnInit} from "@angular/core"
import { BsModalRef } from 'ngx-bootstrap/modal';
import { RallyStage } from './model/rally-stage';

@Component({
    templateUrl : './stage-results.list.component.html'
})
export class StageResultsListComponent implements OnInit {
  @Input() public rallyStage: RallyStage;

  constructor(public modalRef: BsModalRef) {}


    ngOnInit(): void {
    }


}
