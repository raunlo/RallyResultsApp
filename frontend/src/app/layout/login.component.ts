import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {BsModalRef} from "ngx-bootstrap/modal";
import {AuthService} from "../shared/service/auth.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  submitted = false;
  loginForm: any
  @Output() public onClose: EventEmitter<any> = new EventEmitter<any>()

  constructor(public modalRef: BsModalRef, private authService: AuthService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: [undefined, Validators.required],
      password: [undefined, Validators.required]
    })
  }

  login() {
    this.submitted = true
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe(() => {
        this.onClose.emit();
        this.modalRef.hide()
      })
    }
  }

  get username() {
    return this.loginForm.get('username')
  }
  get password() {
    return this.loginForm.get('password')
  }
}
