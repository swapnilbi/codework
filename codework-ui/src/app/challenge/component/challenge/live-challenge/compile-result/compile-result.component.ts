import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ProblemSolutionResult } from 'src/app/challenge/model/problem-solution-result.model';

@Component({
  selector: 'app-compile-result',
  templateUrl: './compile-result.component.html',
  styleUrls: ['./compile-result.component.scss']
})
export class CompileResultComponent implements OnInit {

  public compileResult! : ProblemSolutionResult;  

  constructor(private modalService: BsModalService,public bsModalRef: BsModalRef) { }

  customClass: string = 'customClass';

  ngOnInit(): void {
  }

  hideModal(): void {
    this.bsModalRef.hide();
  }

}
