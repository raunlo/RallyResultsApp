import {Injectable} from "@angular/core";
import {RestService} from "../shared/service/rest.service";
import {Observable} from "rxjs";
import {ApiKey} from "./model/api-key";
import {Page} from "../shared/model/page";

@Injectable()
export class ApiKeysService {

  constructor(private rest: RestService) {
  }

  getPage(page: number): Observable<Page<ApiKey>> {
    return this.rest.getPage('secured-api:/admin/api-key', ApiKey, page - 1)
  }

  saveApiKey(apiKey: ApiKey): Observable<ApiKey> {
    return apiKey.id ? this.rest.put('secured-api:/admin/api-key', apiKey, ApiKey) :
      this.rest.post('secured-api:/admin/api-key', apiKey, ApiKey)
  }

  deleteApiKey(id: string): Observable<object> {
    return this.rest.delete('secured-api:/admin/api-key', id)
  }
}
