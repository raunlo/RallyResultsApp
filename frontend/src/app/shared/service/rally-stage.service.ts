import {Injectable} from "@angular/core";
import {RestService} from "./rest.service";
import {RallyStage} from "../model/rally-stage";
import {Observable} from "rxjs";
import {Page} from "../model/page";

@Injectable()
export class RallyStageService {

  constructor(private restService: RestService) {
  }

  updateOrSaveRallyStage(body: RallyStage): Observable<RallyStage> {
    return body.id ? this.restService.put(`admin/rally${body.rallyId}/stage`, body, RallyStage) :
      this.restService.post(`secured-api:/admin/rally/${body.rallyId}/stage`, body, RallyStage)
  }

  getRallyStagePage(pageNumber: number, rallyId: string): Observable<Page<RallyStage>> {
      return this.restService.getPage(`secured-api:/admin/rally/${rallyId}/stage`, RallyStage, pageNumber -1 );
  }

  deleteRallyStage(rallyStageId: string, rallyId: string) : Observable<object> {
    return this.restService.delete(`secured-api:/admin/rally/${rallyId}/stage`, rallyStageId);
  }

  findStageById(stageId: string, rallyId: string): Observable<RallyStage> {
    return this.restService.get(`secured-api:/admin/rally/${rallyId}/stage/${stageId}`, RallyStage)
  }
}
