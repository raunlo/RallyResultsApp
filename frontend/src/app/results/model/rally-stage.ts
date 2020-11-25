import { JsonProperty } from 'src/app/shared/util/json';
import { RallyStageResults } from "./rally-stage-results"

export class RallyStage {
    public id: string = undefined;
    public trackName: string = undefined;
    public stageNumber: string = undefined;
    public length: string = undefined;
    @JsonProperty({name: "stageResults", clazz: RallyStageResults, isArray: true})
    public stageResults: RallyStageResults[] = undefined;
}
