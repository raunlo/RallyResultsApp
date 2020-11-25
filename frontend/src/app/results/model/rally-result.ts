import {DateDeserializer, JsonProperty} from 'src/app/shared/util/json';
import {RallyStage} from "./rally-stage";

export class RallyResult {
  @JsonProperty({deserilizer: DateDeserializer})
  public endDate: Date = undefined;
  public name: string = undefined;
  @JsonProperty({deserilizer: DateDeserializer})
  public startDate: Date = undefined;
  @JsonProperty({name: "rallyStages", clazz: RallyStage, isArray: true })
  public rallyStages: RallyStage[] = undefined;
}
