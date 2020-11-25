import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Rally} from "../../shared/model/rally";
import {RallyService} from "../../shared/service/rally.service";
import {BsLocaleService} from "ngx-bootstrap/datepicker";
import {defineLocale, etLocale, formatDate} from "ngx-bootstrap/chronos";
import {BsModalRef} from "ngx-bootstrap/modal";

defineLocale("et", etLocale)

@Component({
  templateUrl: "./rally.detail.component.html",
})
export class RallyDetailComponent implements OnInit {
  submitted = false;
  @Output() public onClose: EventEmitter<any> = new EventEmitter()
  rallyForm: any;
  @Input() rally: Rally;
  error: string = undefined;
  alerts: any[] = [];

  constructor(public bsModalRef: BsModalRef, private fb: FormBuilder, private rallyService: RallyService,
              private localeService: BsLocaleService) {
  }

  submitForm() {
    this.submitted = true
    if (this.rallyForm.valid) {
      const rally = this.initModel()
      this.rallyService.saveOrUpdateRally(rally).subscribe(() => {
        this.onClose.emit(rally);
        this.bsModalRef.hide()
      }, (error) => {
      if (error.error && error.error.code === 409) {
        this.alerts.push({
          msg: "Ei saa salvestada rallit! On juba olemas selline ralli samade kuupäevadega ja nimega",
          timeout: 5000,
          type: "danger"
        })
      }
    }
  )
}
}

private
initModel()
:
Rally
{
  return {
    id: (this.rally && this.rally.id),
    startDate: formatDate(this.rallyForm.value.range[0], 'YYYY-MM-DD', 'et'),
    endDate: formatDate(this.rallyForm.value.range[1], 'YYYY-MM-DD', 'et'),
    country: this.rallyForm.value.country || this.rally.country,
    name: this.rallyForm.value.name || this.rally.name
  }
}

ngOnInit()
:
void {
  this.localeService.use('et');
  this.rallyForm = this.fb.group({
    name: [{
      value: this.rally ? this.rally.name : undefined,
      disabled: this.rally !== undefined
    }, Validators.required],
    country: [{
      value: this.rally ? this.rally.country : undefined,
      disabled: this.rally !== undefined
    }, Validators.required],
    range: [this.rally ? [new Date(this.rally.startDate), new Date(this.rally.endDate)] : undefined, Validators.required]
  });
}

get
name()
{
  return this.rallyForm.get('name')
}

get
country()
{
  return this.rallyForm.get('country')
}

get
range()
{
  return this.rallyForm.get('range')
}
}
