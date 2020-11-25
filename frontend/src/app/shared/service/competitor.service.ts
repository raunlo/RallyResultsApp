import {Injectable} from "@angular/core";
import {Observable, of} from "rxjs";
import {Page} from "../model/page";
import {RestService} from "./rest.service";
import {CompetitorPair} from "../model/competitor.pair";
import {HttpParams} from "@angular/common/http";

@Injectable()
export class CompetitorService {

  constructor(private restService: RestService) {
  }

  getPage(page: number) : Observable<Page<CompetitorPair>>{
    return this.restService.getPage('secured-api:/admin/competitor-pair', CompetitorPair, page - 1);
  }

  post(body: CompetitorPair): Observable<CompetitorPair> {
    return this.restService.post('secured-api:/admin/competitor-pair', body, CompetitorPair);
  }

  delete(id: string): Observable<object> {
    return this.restService.delete('secured-api:/admin/competitor-pair', id)
  }

  findByName(name: string): Observable<CompetitorPair[]> {
   return this.restService.getList('secured-api:/admin/competitor-pair/find-by-name', CompetitorPair, new HttpParams().append("name", name))
  }

  findClassByName(name: string): Observable<String[]> {
    return this.restService.getList(`secured-api:/admin/competitor-pair/class/${name}`, String)
  }
}
