import {Component, EventEmitter, Input, LOCALE_ID, OnInit, Output} from "@angular/core";
import {RallyStage} from "../../shared/model/rally-stage";
import {BsModalRef} from "ngx-bootstrap/modal";
import {FormBuilder, Validators} from "@angular/forms";
import {RallyStageService} from "../../shared/service/rally-stage.service";
import {Observable} from "rxjs";
import {CompetitorPair} from "../../shared/model/competitor.pair";

@Component({
  templateUrl: "./rally-stage.detail.component.html"
})
export class RallyStageDetailComponent implements OnInit {
  submitted = false;
  @Output() public onClose: EventEmitter<any> = new EventEmitter<any>();
  @Input() public rallyStage: RallyStage;
  suggestions: Observable<String[]>
  search: string = ""
  rallyStageForm: any;
  alerts: any[] = [];
  conflictAlert: any = {
    timeout: 5000,
    msg: "Antud etapi number on juba ralliga seotud, palun valida muu number",
    type: "danger"
  }

  constructor(private rallyStageService: RallyStageService, public bsModalRef: BsModalRef, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.rallyStageForm = this.fb.group({
      trackName: [this.rallyStage.trackName ? this.rallyStage.trackName : undefined, Validators.required],
      stageNumber: [this.rallyStage.stageNumber ? this.rallyStage.stageNumber : undefined, [Validators.required, Validators.min(0), Validators.pattern("[1-9]+")]],
      stageLength: [this.rallyStage.length ? this.rallyStage.length : undefined, [Validators.required, Validators.pattern("^(?:[1-9]\\d*|0)?(?:\\,\\d+)?$")]],
    })
  }

  submitForm() {
    this.submitted = true;
    if (this.rallyStageForm.valid) {
      const data = this.initModel()
      this.rallyStageService.updateOrSaveRallyStage(data).subscribe(() => {
        this.onClose.emit(data)
        this.bsModalRef.hide()
      }, (error) => {
        if (error.error.code === 409) {
          this.alerts.push( this.conflictAlert)
        }
      })
    }
  }

  initModel(): RallyStage {
    return {
      ...this.rallyStage,
      trackName: this.rallyStageForm.value.trackName || this.rallyStage.trackName,
      stageNumber: this.rallyStageForm.value.stageNumber || this.rallyStage.stageNumber,
      length: this.rallyStageForm.value.stageLength.replace(',', '.') || this.rallyStage.length
    }
  }

  get trackName() {
    return this.rallyStageForm.get("trackName")
  }

  get stageNumber() {
    return this.rallyStageForm.get("stageNumber")
  }

  get trackLength() {
    return this.rallyStageForm.get("stageLength")
  }
}
