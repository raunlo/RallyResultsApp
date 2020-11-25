import {Component, OnInit} from "@angular/core";
import {ApiKey} from "./model/api-key";
import {ApiKeysService} from "./api.keys.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {ApiKeyDetailComponent} from "./api-key.detail.component";
import {ClipboardService} from "ngx-clipboard";
import {faCopy, faTrashAlt} from "@fortawesome/free-regular-svg-icons";

@Component({
  styleUrls: ['./api-key.table.component.scss'],
  templateUrl: './api-key.table.component.html'
})
export class ApiKeyTableComponent implements OnInit {
  apiKeys: ApiKey[] = undefined;
  totalElements: number = 0;
  page: number = 1;
  bsModalRef: BsModalRef = undefined;
  copyIcon = faCopy;
  copiedAlerts: any[] = []
  deleteIcon = faTrashAlt;

  ngOnInit(): void {
    this.fetchData()
  }

  constructor(private modalService: BsModalService, private service: ApiKeysService, private clipboardService: ClipboardService) {
  }

  newApiKey() {
    this.bsModalRef = this.modalService.show(ApiKeyDetailComponent)
    this.bsModalRef.content.onClose.subscribe(() => this.fetchData())
  }

  private fetchData() {
    this.service.getPage(this.page).subscribe(res => {
      this.totalElements = res.totalElements;
      this.apiKeys = res.content;
    })
  }

  delete(id: string) {
    this.service.deleteApiKey(id).subscribe(() => this.fetchData())
  }

  copyKeyToClipBoard(value: string) {
    this.clipboardService.copy(value)
    this.copiedAlerts.push({
      timeout: 5000,
      msg: "Võtme väärtus kopeeriti",
      type: 'info'
    })
  }

  invertActiveField(apiKey: ApiKey) {
    const model = {
      ...apiKey,
      active: !apiKey.active
    }

    this.service.saveApiKey(model).subscribe(() => this.fetchData())
  }
}
