import {Component, OnInit} from "@angular/core";
import {Observable, Observer, of} from "rxjs";
import {map, switchMap} from "rxjs/operators";
import {RallyService} from "../shared/service/rally.service";
import {RallyResult} from "../results/model/rally-result";
import {RallyStage} from "../results/model/rally-stage";
import {CompareService} from "./compare.service";
import {Color, Label} from "ng2-charts";
import {ChartDataSets} from "chart.js";

@Component({
  selector: "compare-competitors",
  templateUrl: './compare.competitors-on-stage.html'
})
export class CompareCompetitorsOnStage implements OnInit {
  shorChart: boolean = false
  rallyName: string = "";
  suggestions: Observable<RallyResult[]>
  selectedRally: RallyResult
  stages: RallyStage[]
  selectedRallyStage: RallyStage
  averageSpeedList: number[];
  competitorList: Label[];
  chartData: ChartDataSets[];

  lineChartOptions = {
    responsive: true
  };

  lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgb(117,156,219)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';


  constructor(private rallyService: RallyService, private compareChartService: CompareService) {
  }

  modelChange($event: any) {
    this.selectedRally = $event.item
    this.rallyName = $event.item.name
    this.stages = $event.item.rallyStages
  }

  ngOnInit(): void {
    this.suggestions = new Observable((obs: Observer<string>) => {
      obs.next(this.rallyName)
    })
      .pipe(
        switchMap((query: string) => {
          if (query) {
            return this.rallyService.findRallyByIdFromPublicApi(query)
              .pipe(
                map(data => data || [])
              )
          }
          return of([])
        })
      )
  }

  onchange() {
    this.selectedRally = undefined;
    this.selectedRallyStage = undefined;
    this.stages = undefined;
  }

  stageSelected() {
    if (this.selectedRally && this.selectedRallyStage) {
      this.fetchChartData()
    }
  }

  fetchChartData() {
    this.compareChartService.getCompetitorCompareData(this.selectedRallyStage.id).subscribe((res) => {
      this.competitorList = []
      this.averageSpeedList = []
      res.forEach(data => {
        this.competitorList.push(data.competitorName);
        this.averageSpeedList.push(data.averageSpeed)
        this.chartData = [{data: this.averageSpeedList, label: "Etapi läibimise keskmine kiirus"}]
        this.shorChart = true;
      })
    })
  }

}
