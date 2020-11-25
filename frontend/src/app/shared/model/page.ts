import {JsonProperty, PageDeserializer} from "../util/json";

export class Page<T> {
  @JsonProperty({name: "content", deserilizer: PageDeserializer})
  public content: T[] = undefined;
  public totalElements: number = undefined;
  @JsonProperty({name: "number", clazz: String})
  public pageNumber: number = undefined;
  public size : number = undefined;
}
