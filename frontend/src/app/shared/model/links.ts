import {JsonProperty} from "../util/json";


export class Links {
  // @JsonProperty({name: "self", clazz: String, deserilizer : SelfLinkDeserializer })
  public selfLink: string = undefined;

  // @JsonProperty({name: "delete", clazz: String, deserilizer : SelfLinkDeserializer })
  public deleteLink: string = undefined;

  // @JsonProperty({name: "update", clazz: String, deserilizer : SelfLinkDeserializer })
  public updateLink: string = undefined;
}
