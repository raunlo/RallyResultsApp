import {Component, OnInit} from "@angular/core";
import {RallyDetailComponent} from "./rally.detail.component";
import {Rally} from "../../shared/model/rally";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {RallyService} from "../../shared/service/rally.service";
import {faEdit, faPlus, faRoad} from "@fortawesome/free-solid-svg-icons";
import {faTrashAlt} from "@fortawesome/free-regular-svg-icons";
import {PageChangedEvent} from "ngx-bootstrap/pagination";
import {Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  templateUrl: "./rally.table.component.html",
  styleUrls: ["./rally.table.component.scss"]
})
export class RallyTableComponent implements OnInit {
  rallies: Rally[] = undefined;
  public bsModalRef: BsModalRef;
  stageIcon = faRoad;
  editIcon = faEdit;
  deleteIcon = faTrashAlt;
  page: number = 1;
  totalItems: number = 0;

  constructor(private modalService: BsModalService, private rallyService: RallyService, private spinnerService: NgxSpinnerService) {
  }

  ngOnInit(): void {
    this.fetchData()
  }

  newRally() {
    this.bsModalRef = this.modalService.show(RallyDetailComponent, {ignoreBackdropClick: true});
    this.bsModalRef.content.onClose.subscribe(() => {
      this.fetchData()
    })
  }

  openRally(id: string) {
    const rally = this.rallies.find(rally => rally.id === id)
    this.bsModalRef = this.modalService.show(RallyDetailComponent,
      {
        initialState: {rally},
        ignoreBackdropClick: true
      }
    )
      this.bsModalRef.content.onClose.subscribe(() => this.fetchData())
  }

  fetchData() {
    this.spinnerService.show()
    this.rallyService.getPage(this.page).subscribe(res => {
      this.rallies = res.content
      this.totalItems = res.totalElements;
      this.spinnerService.hide()
    })
  }

  deleteRally(id: string) {
    this.rallyService.deleteRally(id).subscribe(() => this.fetchData());
  }

  changePage($event: PageChangedEvent) {
    this.page = $event.page;
    this.fetchData()
  }
}
