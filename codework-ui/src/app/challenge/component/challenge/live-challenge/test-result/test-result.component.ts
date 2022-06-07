import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ProblemSolutionResult } from 'src/app/challenge/model/problem-solution-result.model';

@Component({
  selector: 'app-test-result',
  templateUrl: './test-result.component.html',
  styleUrls: ['.././compile-result/compile-result.component.scss']
})
export class TestResultComponent implements OnInit {

  public compileResult! : ProblemSolutionResult;  

  constructor(private modalService: BsModalService,public bsModalRef: BsModalRef) { }

  ngOnInit(): void {
  }

  hideModal(): void {
    this.bsModalRef.hide();
  }
}
