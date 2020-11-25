import {Component, OnInit} from "@angular/core";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {CompetitorDetailComponent} from "./competitor.detail.component";
import {CompetitorService} from "../../shared/service/competitor.service";
import {CompetitorPair} from "../../shared/model/competitor.pair";
import {PageChangedEvent} from "ngx-bootstrap/pagination";
import {faTrashAlt} from "@fortawesome/free-regular-svg-icons";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  templateUrl: "./competitor.table.component.html"
})
export class CompetitorTableComponent implements OnInit{
  cannotDeleteAlert : any = {
    type: 'danger',
    msg: "Võistluspaariga on seostatud etapi tulemus(ed) ning ei ole võimalik kustutada",
    timeout: 5000
  }

  alerts: any[] = [];
  competitorPairs: CompetitorPair[] = undefined;
  modalRef: BsModalRef;
  page: number = 1;
  totalElements: number = 0;
  deleteIcon = faTrashAlt;

  constructor(private modalService: BsModalService, private competitorService: CompetitorService,
              private spinnerService: NgxSpinnerService) {
  }


  addNewCompetitorPair() {
    this.modalRef = this.modalService.show(CompetitorDetailComponent)
    this.modalRef.content.onClose.subscribe(() => this.fetchData())
  }

  fetchData() {
    this.competitorService.getPage(this.page).subscribe((res) => {
      this.totalElements = res.totalElements;
      this.competitorPairs = res.content;
    })
  }

  changePage($event: PageChangedEvent) {
    this.page = $event.page;
    this.fetchData()
  }

  deleteCompetitorPair(id: string) {
    this.competitorService.delete(id).subscribe(() => this.fetchData(),
        error => {
      if(error.error.code === 422) {
        this.alerts.push(this.cannotDeleteAlert)
      }
    })
  }

  ngOnInit(): void {
    this.fetchData()
  }
}
