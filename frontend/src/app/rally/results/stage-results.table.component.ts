import {Component, OnInit} from "@angular/core";
import {StageResult} from "../../shared/model/stage-result";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {StageResultsDetailsComponent} from "./stage-results.details.component";
import {ActivatedRoute} from "@angular/router";
import {RallyStage} from "../../shared/model/rally-stage";
import {RallyStageService} from "../../shared/service/rally-stage.service";
import {StageResultsService} from "../../shared/service/stage-results.service";
import {PageChangedEvent} from "ngx-bootstrap/pagination";
import {faTrashAlt} from "@fortawesome/free-regular-svg-icons";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  templateUrl: './stage-results.table.component.html'
})
export class StageResultsTableComponent implements OnInit {
  rallyId: string = undefined;
  stage: RallyStage = undefined;
  results: StageResult[] = undefined;
  page: number = 1;
  totalElements: number = 0;
  deleteIcon = faTrashAlt
  modalRef: BsModalRef
  alerts: any[] = [];

  constructor(private modalService: BsModalService,
              private route: ActivatedRoute,
              private stageService: RallyStageService,
              private stageResultService: StageResultsService,
              private spinnerService: NgxSpinnerService) {
  }

  ngOnInit(): void {
    this.rallyId = this.route.snapshot.paramMap.get("rallyId")
    const stageId = this.route.snapshot.paramMap.get('stageId')
    this.stageService.findStageById(stageId, this.rallyId).subscribe(res => {
      this.stage = res
      this.fetchData()
    })

  }

  fetchData() {
    this.spinnerService.show()
    this.stageResultService.getPage(this.page, this.rallyId, this.stage.id).subscribe(res => {
      this.results = res.content;
      this.totalElements = res.totalElements;
      this.spinnerService.hide()
    }, (error) => this.alerts.push({
      message: error.error.error,
      timeout: 5000,
      type: "danger"
    }))
  }

  createStageResult() {
    const stageResult: StageResult = {
      ...new StageResult(),
      rallyId: this.rallyId,
      rallyStageId: this.stage.id
    }
    this.modalRef = this.modalService.show(StageResultsDetailsComponent,
      {
        initialState: {stageResult}
      })
    this.modalRef.content.onClose.subscribe(() => this.fetchData())
  }

  changePage($event: PageChangedEvent) {
    this.page = $event.page
    this.fetchData()
  }

  deleteResult(id: string) {
    this.stageResultService.deleteResult(id, this.rallyId, this.stage.id).subscribe(() => this.fetchData());
  }
}
