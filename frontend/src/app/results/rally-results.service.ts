import {Injectable} from "@angular/core";
import {RestService} from "../shared/service/rest.service";
import {HttpParams} from "@angular/common/http";
import {RallyResult} from "./model/rally-result";
import {Observable} from "rxjs";
import {Page} from "../shared/model/page";

@Injectable()
export class RallyResultsService {

  constructor(private rest: RestService) {
  }

  findRallyResults(rallyName: string, stageName: string, competitorName: string, page: number ): Observable<Page<RallyResult>> {
    const params = new HttpParams().append("rallyName", rallyName)
      .append("stageName", stageName)
      .append("competitorName", competitorName)

    return this.rest.getPage('public-api:/search/rally-results', RallyResult, page - 1, params)
  }
}
