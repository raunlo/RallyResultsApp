import {Component, OnInit} from "@angular/core";
import {RallyStage} from "../../shared/model/rally-stage";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {RallyStageService} from "../../shared/service/rally-stage.service";
import {RallyStageDetailComponent} from "./rally-stage.detail.component";
import {ActivatedRoute, Router} from "@angular/router";
import {PageChangedEvent} from "ngx-bootstrap/pagination";
import {faTrashAlt} from "@fortawesome/free-regular-svg-icons";
import {RallyService} from "../../shared/service/rally.service";
import {Rally} from "../../shared/model/rally";
import {NgxSpinnerService} from "ngx-spinner";
import {throwError} from "rxjs";

@Component({
  templateUrl: "./rally-stage.table.component.html"
})
export class RallyStageTableComponent implements OnInit {
  page: number = 1;
  rallyStages: RallyStage[] = undefined;
  totalElements: number = 0;
  bsModalRef: BsModalRef;
  rally: Rally;
  deleteIcon = faTrashAlt;

  constructor(private modalService: BsModalService, private rallyStageService: RallyStageService,
              private route: ActivatedRoute, private rallyService: RallyService,
              private spinnerService: NgxSpinnerService, private router: Router) {}

  fetchData() {
    this.spinnerService.show()
    this.rallyStageService.getRallyStagePage(this.page, this.rally.id).subscribe((res) => {
      this.page = res.pageNumber + 1
      this.totalElements = res.totalElements
      this.rallyStages = res.content
      this.spinnerService.hide()
    })
  }

  createNewRallyStage() {
    const rallyStage: RallyStage = {
      id: undefined,
      rallyId: this.rally.id,
      trackName: undefined,
      stageNumber: undefined,
      length: undefined
    }
    this.bsModalRef = this.modalService.show(RallyStageDetailComponent, {
      initialState: {rallyStage}
    })
    this.bsModalRef.content.onClose.subscribe(() => this.fetchData())
  }

  openRallyStageDetails(rallyStage: RallyStage) {
    this.bsModalRef = this.modalService.show(RallyStageDetailComponent, {initialState: {rallyStage}})
    this.bsModalRef.content.onClose.subscribe(() => this.fetchData())
  }

  ngOnInit(): void {
    const rallyId = this.route.snapshot.paramMap.get("rallyId")
    this.rallyService.findRallyById(rallyId).subscribe(rally => {
      this.rally = rally
      this.fetchData()
    })
  }

  changePage($event: PageChangedEvent) {
    this.page = $event.page
    this.fetchData()
  }

  deleteStage(id: string) {
    this.rallyStageService.deleteRallyStage(id, this.rally.id).subscribe(() => this.fetchData())
  }

  openStageResults (rallyId: string, rallyStageId: string) {
    this.router.navigateByUrl(`admin/rally/${rallyId}/stages/${rallyStageId}/result`).catch(error => throwError(error))
  }
}
