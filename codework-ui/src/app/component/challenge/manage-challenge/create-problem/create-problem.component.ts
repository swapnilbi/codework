import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/component/common/loader/loader.service';
import { Language, Problem, ProblemType, TestCase } from 'src/app/model/problem.model';
import { ProblemService } from 'src/app/service/problem.service';

@Component({
  selector: 'app-create-problem',
  templateUrl: './create-problem.component.html',
  styleUrls: ['./create-problem.component.scss']
})
export class CreateProblemComponent implements OnInit {

  instanceId : any;
  problemId : any;
  problemTypes : Array<string> = [
    'PROGRAM',
    'PUZZLE'
  ]
  languagesAllowedList : Array<Language> = [];
  problemForm: FormGroup;

  ngOnInit(): void {
    this.instanceId = this.route.snapshot.paramMap.get('instanceId');        
    this.problemId = this.route.snapshot.paramMap.get('problemId');        
    this.problemService.getLanguages().subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.languagesAllowedList = response;
          if(this.instanceId){
            this.setDefaultValues();   
            this.addTestCase();
            this.addPlaceHolderSolution();       
          }else if(this.problemId){
            this.problemService.getProblem(this.problemId).subscribe(problem => {  
              let _this = this;
              if(problem.type ==  ProblemType.PROGRAM){
                if(problem.testCases && problem.testCases.length > 0){
                  problem.testCases.forEach(function (testCase: any) {
                     _this.addTestCase();
                  });                  
                }   
                if(problem.placeHolderSolution){
                  let placeHolderSolutions = [];
                  for(let i in problem.placeHolderSolution){
                    _this.addPlaceHolderSolution();
                    placeHolderSolutions.push({
                      id : i,
                      solution : problem.placeHolderSolution[i]
                    })
                  }                       
                  problem.placeHolderSolutions = placeHolderSolutions;
                }
              }else{
                this.addTestCase();
                this.addPlaceHolderSolution();    
              }                        
              this.problemForm.patchValue(problem);              
            });
          }          
        }                
      }, error => {
        this.loaderService.hide();       
    });    
  }

  

  constructor(private route: ActivatedRoute,
    private problemService : ProblemService,
    private router : Router,
    private location : Location,
    private fb:FormBuilder,
    private alertService : AlertService, 
    private loaderService: LoaderService) {      
      this.problemForm = this.fb.group({
        "name": new FormControl("", Validators.required),
        "problemStatement": new FormControl("", Validators.required),
        "type": new FormControl(null, Validators.required),
        "languagesAllowed" : new FormControl([]),
        "memoryLimit" :  new FormControl(null),
        "cpuLimit" :new FormControl(null),        
        "pointSystem" : this.fb.group({
          "correctAnswer" :new FormControl(null),
          "bestSolutionBonus" :new FormControl(null),
          "minNumberOfTc" :new FormControl(null),
          "quickSolutionBonus" :new FormControl(null),
          "splitPointsByTc" :new FormControl(null)
        }),
        "testCases" : this.fb.array([]),
        "placeHolderSolutions" : this.fb.array([]),
     });     
  }

  lagnuageCompare(c1: Language, c2: Language): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  lagnuageIdCompare(c1: any, c2: any): boolean {    
    return c1 && c2 ? c1 == c2 : false;
  }

  back(){        
    this.location.back();
  }

  addTestCase() {
    const testCaseForm = this.fb.group({
      input: new FormControl(""),
      expectedOutput: new FormControl(""),
      isSample: new FormControl(false)
    })
    this.testCases.push(testCaseForm);
  }

  deleteTestCase(lessonIndex: number) {
    this.testCases.removeAt(lessonIndex);
  }

  get testCases() {
    return this.problemForm.controls["testCases"] as FormArray;
  }

  addPlaceHolderSolution() {
    const placeHolderSolutionForm = this.fb.group({
      id: new FormControl(),
      solution: new FormControl(""),      
    })
    this.placeHolderSolutions.push(placeHolderSolutionForm);
  }

  deletePlaceHolderSolution(lessonIndex: number) {
    this.placeHolderSolutions.removeAt(lessonIndex);
  }

  get placeHolderSolutions() {
    return this.problemForm.controls["placeHolderSolutions"] as FormArray;
  }

  createProblem(problemForm : any){       
    problemForm = this.getProblemForm(problemForm);
    problemForm.challengeInstanceId = this.instanceId;    
    this.loaderService.show();       
    this.problemService.createProblem(problemForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Problem has been created successfully");
          this.showProblems();
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  getProblemForm(problemForm : any){
     /*
       TODO validations
    */
    let placeHolderSolution : any = {};
    if(problemForm.placeHolderSolutions && problemForm.placeHolderSolutions.length > 0){
      problemForm.placeHolderSolutions.forEach(function (solution: any) {
        if(solution.id){
          placeHolderSolution[solution.id] = solution.solution;
        }        
      });        
    }	
    problemForm.placeHolderSolution = placeHolderSolution;
    if(problemForm.testCases && problemForm.testCases.length > 0){
      let testCases : Array<TestCase> = []
      problemForm.testCases.forEach(function (testCase: any) {
        if(testCase.input && testCase.input.trim()!=''){
          testCases.push(testCase);
        }        
      });        
      problemForm.testCases = testCases;
    }	
    return problemForm;
  }

  updateProblem(problemForm : any){           
    problemForm = this.getProblemForm(problemForm);
    problemForm.id = this.problemId;    
    this.loaderService.show();       
    this.problemService.updateProblem(problemForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Problem has been updated successfully");
          this.location.back();
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  showProblems(){    
    var url = 'challenge/instance/'+this.instanceId+'/problem/manage';
    this.router.navigateByUrl(url);    
  }
  
  setDefaultValues(){
    let problem = {      
      "name" : "Test",
      "problemStatement" : "test statement",
      "type" : "PROGRAM",      
      "memoryLimit" : 128000,
      "cpuLimit" : 10,
      "pointSystem" : {        
        "correctAnswer" : 100,
        "bestSolutionBonus" : 0,
        "quickSolutionBonus" : 0,
        "minNumberOfTc" : 7      
      }      
    }
    this.problemForm.patchValue(problem);
  }

}
