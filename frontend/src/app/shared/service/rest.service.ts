import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Page} from "../model/page";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map} from "rxjs/operators";
import JsonUtils from "../util/json";


@Injectable()
export class RestService {

  constructor(private http: HttpClient) {
  }

  post<T>(path: string, body: T, clazz: { new(): T }, params?: HttpParams): Observable<T> {
    return this.http.post<T>(path, JsonUtils.serialize(body), {params})
      .pipe(
        map(json => JsonUtils.deserialize(new clazz(), json) as T)
      )
  }

  put<T>(path: string, body: T, clazz: { new(): T }, params?: HttpParams): Observable<T> {
    return this.http.put(path, JsonUtils.serialize(body), {params})
      .pipe(
        map(json => JsonUtils.deserialize(new clazz(), json) as T)
      )
  }

  get<T>(path: string, clazz: { new(): T }, params?: HttpParams): Observable<T> {
    return this.http.get(path, {params})
      .pipe(
        map(json => JsonUtils.deserialize(new clazz(), json) as T)
      );
  }

  getList<T>(path: string, clazz: { new(): T }, params?: HttpParams): Observable<T[]> {
    return this.http.get<T[]>(path, {params})
      .pipe(
        map(json => {
            return json.map(obj => JsonUtils.deserialize(new clazz(), obj) as T
            )
          }
        )
      )
  }

  getPage<T>(path: string, clazz: { new(): T }, pageNumber: number, params?: HttpParams): Observable<Page<T>> {
    const httpParams = RestService.createPagingHttpParams(pageNumber, params)
    return this.http.get(path, {
      params: httpParams,
      headers: {"content-type": "application/json"}
    })
      .pipe(
        map(json => {
          Reflect.defineMetadata('jsonProperty', {name: 'content', clazz: clazz}, json,
            'content');
          return JsonUtils.deserialize(new Page, json) as Page<T>;
        })
      )
  }


  private static createPagingHttpParams(pageNumber: number, params?: HttpParams): HttpParams {
    if (!params) {
      params = new HttpParams();
    }
    params = params.append("page", String(pageNumber))
    return params
  }

  delete(path: string, id: string, params?: HttpParams): Observable<object> {
    return this.http.delete(path + `/${id}`, {
      params,
      headers: {"content-type": "application/json"}
    })
  }
}
