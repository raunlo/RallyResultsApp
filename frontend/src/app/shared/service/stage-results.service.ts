import {Injectable} from "@angular/core";
import {RestService} from "./rest.service";
import {Observable} from "rxjs";
import {StageResult} from "../model/stage-result";

@Injectable()
export class StageResultsService {

  constructor(private restService: RestService) {
  }

  save(body: StageResult): Observable<StageResult> {
    return this.restService.post(`secured-api:/admin/${body.rallyId}/stage/${body.rallyStageId}/results`, {
      ...body,
      rallyId: undefined
    }, StageResult)
  }

  getPage(page: number, rallyId: string, stageId: string) {
    return this.restService.getPage(`secured-api:/admin/${rallyId}/stage/${stageId}/results`, StageResult, page - 1)
  }

  deleteResult(resultId: string, rallyId: string, stageId: string): Observable<object> {
   return this.restService.delete(`secured-api:/admin/${rallyId}/stage/${stageId}/results`, resultId)
  }
}
