import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {AppConfig} from "../../app.config";
import {BsModalService} from "ngx-bootstrap/modal";
import {Router} from "@angular/router";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient, private appConfig: AppConfig, private modalService: BsModalService, private router: Router) {
  }

  login(username: string, password: string): Observable<boolean> {
    const headers = {
      headers: new HttpHeaders({
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
      })
    };

    const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;

    return this.http.post('oauth:/token', body, headers).pipe(
      map(json => json['access_token']),
      map(token => {
        if (token) {
          this.appConfig.saveToken(token)
          return true;
        } else {
          return false;
        }
      })
    )
  }

  isAuthenticated() {
    return !!this.appConfig.getToken();
  }

  logout() {
    this.appConfig.deleteToken()
    this.modalService.hide(1)
    this.router.navigate(["/"])
  }

}
