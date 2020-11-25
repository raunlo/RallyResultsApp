import {Injectable} from "@angular/core";
import {RestService} from "../shared/service/rest.service";
import {Observable} from "rxjs";
import {CompareData} from "./model/compare.data";
import {HttpParams} from "@angular/common/http";

@Injectable()
export class CompareService {

  constructor(private restService: RestService) {
  }

  getCompetitorCompareData(stageId: string): Observable<CompareData[]> {
    const httpParams = new HttpParams().append("stageId", stageId)
    return this.restService.getList(`public-api:/compare/average-speed/${stageId}`, CompareData, httpParams)
  }

}
