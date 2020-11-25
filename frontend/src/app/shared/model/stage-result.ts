import {CompetitorPair} from "./competitor.pair";

export class StageResult {
  public id: string = undefined;
  public competitor: CompetitorPair = undefined;
  public interrupted: boolean = undefined;
  public time: string = undefined;
  public rallyStageId: string = undefined;
  public rallyId: string = undefined;
}
