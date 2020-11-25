import {Pipe, PipeTransform} from "@angular/core";
import {CompetitorPair} from "../model/competitor.pair";

@Pipe({
  name: "driverAndCoDriverName"
})
export class DriverAndCoDriverNamePipe implements PipeTransform{
  transform(value: any, ...args: any[]): any {
    if(!(value instanceof CompetitorPair)) return undefined;

    return `${value.driver.firstName} ${value.driver.lastName}/${value.coDriver.firstName} ${value.coDriver.lastName}`
  }

}
