import {RestService} from "./rest.service";
import {Rally} from "../model/rally";
import {Injectable} from "@angular/core";
import {Page} from "../model/page";
import {Observable} from "rxjs";
import {HttpParams} from "@angular/common/http";
import {RallyResult} from "../../results/model/rally-result";

@Injectable()
export class RallyService {

  constructor(private restService: RestService) {
  }

  saveOrUpdateRally(body: Rally): Observable<Rally> {
    return body.id ? this.restService.put('secured-api:/admin/rally', body, Rally) :
      this.restService.post('secured-api:/admin/rally', body, Rally)
  }

  getPage(page: number): Observable<Page<Rally>> {
    return this.restService.getPage<Rally>('secured-api:/admin/rally', Rally, page - 1)
  }

  deleteRally(id: string): Observable<object> {
    return this.restService.delete('secured-api:/admin/rally', id)
  }

  findRallyById(id: string): Observable<Rally> {
    return this.restService.get(`secured-api:/admin/rally/${id}`, Rally)
  }

  findRallyByIdFromPublicApi(query: string) : Observable<RallyResult[]> {
    return this.restService.getList(`public-api:/search/rallies/search-by-name/${query}`, RallyResult);
  }
}
