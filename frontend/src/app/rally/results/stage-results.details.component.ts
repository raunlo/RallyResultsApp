import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {BsModalRef} from "ngx-bootstrap/modal";
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {StageResultsService} from "../../shared/service/stage-results.service";
import {StageResult} from "../../shared/model/stage-result";
import {Observable, Observer, of} from "rxjs";
import {map, switchMap} from "rxjs/operators";
import {CompetitorPair} from "../../shared/model/competitor.pair";
import {CompetitorService} from "../../shared/service/competitor.service";

@Component({
  templateUrl: './stage-results.details.component.html'
})
export class StageResultsDetailsComponent implements OnInit {
  submitted = false;
  stageResultForm: any;
  search: string = ""
  noResult = false;
  suggestions: Observable<CompetitorPair[]>
  selectedCompetitor: CompetitorPair;
  error: string;
  @Input() public stageResult: StageResult;
  @Output() public onClose: EventEmitter<any> = new EventEmitter<any>()
  alerts: any[] = []

  constructor(public modalRef: BsModalRef, private fb: FormBuilder,
              private stageResultService: StageResultsService,
              private competitorService: CompetitorService) {
  }

  initModel(): StageResult {
    return {
      ...this.stageResult,
      interrupted: this.stageResultForm.value.interrupted,
      time: this.stageResultForm.value.time ? this.stageResultForm.value.time.replace(",", ".") : undefined,
      competitor: this.selectedCompetitor,
      id: undefined,
    }
  }

  submitForm() {
    console.log("fdsfdsfds")
    this.submitted = true
    if (this.stageResultForm.valid) {
      const model = this.initModel()
      this.stageResultService.save(model).subscribe((res) => {
        this.onClose.emit(res)
        this.modalRef.hide()
      }, (error) => {
        if(error.error.code === 409) {
          this.alerts.push({
            msg: "Ei saanud salvestada tulemust. Sellise võistluspaariga on tulemus juba olemas",
            timeout: 5000,
            type: "danger"
          })
        }
      })
    }
  }

  ngOnInit(): void {
    this.stageResultForm = this.initForm()
    this.suggestions = new Observable((obs: Observer<string>) => {
      obs.next(this.stageResultForm.value.competitor)
    })
      .pipe(
        switchMap((query: string) => {
          if (query) {
            return this.competitorService.findByName(query)
              .pipe(
                map(data => data || [])
              )
          }
          return of([])
        }))
    this.timeAndInterruptedConditionalValidation()
  }

  private initForm() {
    return this.fb.group({
      interrupted: [undefined],
      time: [undefined],
      competitor: [undefined, Validators.required]
    });
  }

   timeAndInterruptedConditionalValidation() {
        if (!this.interrupted.value) {
          this.stageResultForm.get('interrupted').clearValidators()
          this.stageResultForm.get('time').setValidators([Validators.required, Validators.pattern("^\\d{2}:\\d{2}[.,]\\d{1}$")])
        } else {
          this.stageResultForm.get('interrupted').setValidators([Validators.required])
          this.stageResultForm.get('time').clearValidators()
        }
     this.stageResultForm.get('time').updateValueAndValidity()
     this.stageResultForm.get('interrupted').updateValueAndValidity()
      }

  modelChange($event: any) {
    this.selectedCompetitor = $event.item;
    this.stageResultForm.setValue({
      competitor: this.selectedCompetitor.driver.firstName + ' ' +
        this.selectedCompetitor.driver.lastName + '/' +
        this.selectedCompetitor.coDriver.firstName + ' ' +
        this.selectedCompetitor.coDriver.lastName,
      interrupted: this.stageResultForm.value.interrupted,
      time: this.stageResultForm.value.time
    })
  }

  get interrupted() {
    return this.stageResultForm.get("interrupted")
  }

  get time() {
    return this.stageResultForm.get("time")
  }

  get competitor() {
    return this.stageResultForm.get("competitor")
  }

  noResultsTypeAhead($event: boolean) {
    this.noResult = $event
  }
}
