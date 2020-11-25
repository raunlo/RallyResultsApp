import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {BsModalRef} from "ngx-bootstrap/modal";
import {FormBuilder, Validators} from "@angular/forms";
import {CompetitorService} from "../../shared/service/competitor.service";
import {CompetitorPair} from "../../shared/model/competitor.pair";
import {CompetitionClass} from "../../shared/model/competition.class";
import {Observable, Observer, of} from "rxjs";
import {map, switchMap} from "rxjs/operators";

@Component({
  templateUrl: "./competitor.detail.component.html"
})
export class CompetitorDetailComponent implements OnInit {
  submitted = false;
  competitionClasses = CompetitionClass;
  search = ""
  noResult = false;
  suggestions: Observable<String[]>
  competitorForm: any;
  @Input() public competitorPair: CompetitorPair = undefined;
  @Output() public onClose: EventEmitter<any> = new EventEmitter<any>()


  constructor(public bsModalRef: BsModalRef, private fb: FormBuilder, private competitorService: CompetitorService) {
  }

  submitForm() {
    this.submitted = true
    if (this.competitorForm.valid) {
      const model = this.initModel()
      this.competitorService.post(model).subscribe((res) => {
        this.onClose.emit(res)
        this.bsModalRef.hide()
      })
    }
  }

  initModel(): CompetitorPair {
    return {
      id: (this.competitorPair && this.competitorPair.id) || undefined,
      driver: {
        firstName: (this.competitorPair && this.competitorPair.driver.firstName) || this.competitorForm.value.driverFirstName,
        lastName: (this.competitorPair && this.competitorPair.driver.lastName) ||  this.competitorForm.value.driverLastName,
        id: (this.competitorPair && this.competitorPair.driver.id) ||  undefined,
      },
      coDriver: {
        firstName: (this.competitorPair && this.competitorPair.coDriver.firstName) || this.competitorForm.value.coDriverFirstName,
        lastName: (this.competitorPair && this.competitorPair.coDriver.lastName) || this.competitorForm.value.coDriverLastName,
        id: (this.competitorPair && this.competitorPair.coDriver.id) ||  undefined,
      }, competitionClass: this.competitionClass.value
    }
  }

  ngOnInit(): void {

    this.competitorForm = this.fb.group({
      driverFirstName: [undefined, Validators.required],
      driverLastName: [ undefined, Validators.required],
      coDriverFirstName: [undefined, Validators.required],
      coDriverLastName: [undefined, Validators.required],
      competitionClass: ["" , [Validators.required]]
    })

    this.suggestions = new Observable((obs: Observer<string>) => {
      obs.next(this.competitorForm.value.competitionClass)
    })
      .pipe(
        switchMap((query: string) => {
          if (query) {
            return  this.competitorService.findClassByName(query)
          }
          return of([])
        }))
  }

  get competitionClass() { return this.competitorForm.get("competitionClass")}
  get driverFirstName() { return this.competitorForm.get("driverFirstName")}
  get driverLastName() { return this.competitorForm.get("driverLastName")}
  get coDriverFirstName() { return this.competitorForm.get("coDriverFirstName")}
  get coDriverLastName() { return this.competitorForm.get("coDriverLastName")}

}
