import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {faUserAlt} from "@fortawesome/free-solid-svg-icons/faUserAlt";
import {faSignOutAlt} from "@fortawesome/free-solid-svg-icons/faSignOutAlt";
import {faSignInAlt} from "@fortawesome/free-solid-svg-icons/faSignInAlt";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {LoginComponent} from "./login.component";
import {AuthService} from "../shared/service/auth.service";

@Component({
  templateUrl : './layout.component.html',
  styleUrls : ['./layout.component.scss']

})
export class LayoutComponent {
  name: string = "ADMIN"
  logoutIcon = faSignOutAlt;
  signIn = faSignInAlt
  user = faUserAlt;
  bsModalRef: BsModalRef

  constructor(private modalService: BsModalService, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {

  }

  login() {
    this.bsModalRef = this.modalService.show(LoginComponent)
    this.bsModalRef.content.onClose.subscribe(() => {
      this.router.navigate(["/"])
    })
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["/"])
  }

  isAuthenticated() {
    return this.authService.isAuthenticated()
  }

}
