import {Component, EventEmitter, Input, Output} from "@angular/core";
import {ApiKey} from "./model/api-key";
import {FormBuilder, Validators} from "@angular/forms";
import {ApiKeysService} from "./api.keys.service";
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  styleUrls: ['./api-key.detail.component.scss'],
  templateUrl: './api-key.detail.component.html'
})
export class ApiKeyDetailComponent {

  submitted = false;
  @Output() public onClose: EventEmitter<any> = new EventEmitter<any>();
  apiKeyForm: any;
  error: string = undefined;
  alerts: any[]


  constructor(public bsModalRef: BsModalRef, private service: ApiKeysService, private fb: FormBuilder) {
  }

  submitForm() {
    this.submitted = true
    if (this.apiKeyForm.valid) {
      this.service.saveApiKey(this.initModel()).subscribe(res => {
        this.onClose.emit(res);
        this.bsModalRef.hide()
      }, (error) => this.alerts.push({
        type: 'danger',
        msg: error.error.error,
        timeout: 5000
      }))
    }
  }

  initModel(): ApiKey {
    return {
      id: undefined,
      firstName: this.apiKeyForm.value.firstName,
      lastName: this.apiKeyForm.value.lastName,
      active: true,
      email: this.apiKeyForm.value.email,
      telNumber: this.apiKeyForm.value.telNumber || undefined,
      value: undefined,
    }
  }

  ngOnInit(): void {
    this.apiKeyForm = this.fb.group({
      firstName: [{
        value: undefined,
        disabled: undefined
      }, Validators.required],
      lastName: [{
        value: undefined,
        disabled: undefined
      }, Validators.required],
      email: [undefined, [Validators.email, Validators.required]],
      telNumber: [undefined]
    });
  }

  get firstName() {
    return this.apiKeyForm.get('firstName')
  }

  get lastName() {
    return this.apiKeyForm.get('lastName')
  }

  get email() {
    return this.apiKeyForm.get('email')
  }

  get telNumber() {
    return this.apiKeyForm.get('telNumber')
  }
}
