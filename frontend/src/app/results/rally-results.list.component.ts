import {Component, OnInit} from "@angular/core";
import {RallyResultsService} from "./rally-results.service";
import {RallyResult} from "./model/rally-result";
import { BsModalService } from 'ngx-bootstrap/modal';
import { StageResultsListComponent } from './stage-results.list.component';
import {RallyStage} from "./model/rally-stage";
import {NgxSpinnerService} from "ngx-spinner";
import {PageChangedEvent} from "ngx-bootstrap/pagination";


@Component({
  templateUrl: './rally-results.list.component.html'
})
export class RallyResultsListComponent implements OnInit {
  rallyName: string = ""
  stageName: string = ""
  competitorName: string = ""
  results: RallyResult[] = undefined;
  page = 1;
  totalElements = 0;
  bsModalRef: any;

  constructor(private rallyResultsService: RallyResultsService, private modalService: BsModalService,
              private spinnerService: NgxSpinnerService) {
  }

  ngOnInit(): void {
    this.fetchData()
  }

  fetchData() {
    this.spinnerService.show()
   this.rallyResultsService.findRallyResults(this.rallyName, this.stageName, this.competitorName, this.page)
    .subscribe((res) => {
      this.totalElements = res.totalElements
      this.results = res.content;
      this.spinnerService.hide()
    })
  }

  filterResults() {
    this.fetchData()
  }

  openResults(rallyStage: RallyStage) {
    this.bsModalRef = this.modalService.show(StageResultsListComponent, {initialState: {rallyStage}})
  }

  changePage($event: PageChangedEvent) {
    this.page = $event.page
    this.fetchData()
  }
}
