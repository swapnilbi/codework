import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'timeTaken',
    pure: false
})
export class TimeTakenPipe implements PipeTransform {
    transform(timeTaken: any): any {
        // I am unsure what id is here. did you mean title?
        if(timeTaken){
            let timeTakenStr : string = '0';              
            let totalTimeTaken = timeTaken / 1000;
            if(totalTimeTaken < 60){
                timeTakenStr = Math.round(totalTimeTaken) + ' Sec';
            }else if(totalTimeTaken >= 60 * 60){
                let totalHrsTaken = (totalTimeTaken/60)/60;                          
                let rem = (totalHrsTaken- Math.floor(totalHrsTaken)) * 60;
                timeTakenStr = Math.floor(totalHrsTaken) + ' Hour ';
                if(Math.round(rem) > 0 ){
                    timeTakenStr += Math.round(rem)+" Min";
                }
            }else{
                let totalMinTaken = totalTimeTaken/60;          
                let rem = totalTimeTaken % 60;
                timeTakenStr = Math.floor(totalMinTaken) + ' Min ';
                if(Math.round(rem) > 0){
                    timeTakenStr += Math.round(rem)+ " Sec"; 
                }
            }   
            return timeTakenStr;             
        }
        return timeTaken;        
    }
}