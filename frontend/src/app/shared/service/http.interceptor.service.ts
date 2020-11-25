import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, ObservableInput} from "rxjs";
import {AppConfig} from "../../app.config";
import {Router} from "@angular/router";
import {BsModalService} from "ngx-bootstrap/modal";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {


  constructor(private appConfig: AppConfig, private router: Router, private modalService: BsModalService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const reqUrl = req.clone({
        url: this.processUrl(req.url),
        headers: this.setHeaders(req),
        withCredentials: true,
      }
    );

    return next.handle(reqUrl).pipe(
      tap(() => {
      }),
      catchError((error) => this.handleError(error))
    );
  }

  private processUrl(url: string) {
    switch (url.substring(0, url.indexOf('/'))) {
      case 'secured-api:':
      case 'public-api:':
        return this.appConfig.getApiUrl() + url.substring(url.indexOf('/') + 1);
      case 'oauth:':
        return this.appConfig.getOAuthUrl() + url.substring(url.indexOf('/') + 1);
      default:
        throw new Error('not implemented url type');
    }
  }

  private setHeaders(req: HttpRequest<any>) {
    switch (req.url.substring(0, req.url.indexOf('/'))) {
      case 'secured-api:':
        req.headers.set('Content-Type', 'application/json');
        return req.headers.set('Authorization', 'Bearer ' + this.appConfig.getToken());
      case 'public-api:':
        return req.headers.set('Content-Type', 'application/json');
      case 'oauth:':
        return req.headers.set('Authorization', 'Basic ' + "cmFsbHlfcmVzdWx0czpyYWxseV9zZWNyZXRz");
      default:
        throw new Error('not implemented url type');
    }
  }

  handleError(error: HttpErrorResponse): ObservableInput<any> {
    if (error.status === 401 || error.status === 403) {
      this.appConfig.deleteToken();
      this.modalService.hide(2)
      this.router.navigate(["error-401"])
    }
    throw error;
  }
}
