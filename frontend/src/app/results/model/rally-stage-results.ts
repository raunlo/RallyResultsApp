import {CompetitorPair} from "../../shared/model/competitor.pair";
import {JsonProperty} from "../../shared/util/json";

export class RallyStageResults {
    public length: string = undefined;
    public time: string = undefined;
    public interrupted: boolean = undefined;
    @JsonProperty({clazz: CompetitorPair})
    public competitor: CompetitorPair = undefined;
}
