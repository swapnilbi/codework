import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/component/common/loader/loader.service';
import { Problem } from 'src/app/model/problem.model';
import { ProblemService } from 'src/app/service/problem.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-manage-problem',
  templateUrl: './manage-problem.component.html',
  styleUrls: ['./manage-problem.component.scss']
})
export class ManageProblemComponent implements OnInit {

  problems : Array<Problem>;
  instanceId : any;

  constructor(private problemService : ProblemService, 
    private router : Router, 
    private location: Location,
    private alertService : AlertService, 
    private loaderService: LoaderService,
    private route: ActivatedRoute) {
    this.problems = [];
  }

  ngOnInit(): void {
    this.instanceId = this.route.snapshot.paramMap.get('id');    
    this.getProblems()
  }

  getProblems(){
    this.problemService.getProblems(this.instanceId).subscribe(response => {
      this.problems = response;
    })
  }

  createProblem(){
    var url = 'challenge/instance/'+this.instanceId+'/problem/create';
    this.router.navigateByUrl(url);
  }

  back(){        
    this.location.back();
  }

  editProblem(problem : Problem){
    var url = 'challenge/instance/problem/'+problem.id+'/edit';
    this.router.navigateByUrl(url);
  }
  
  deleteProblem(problem : Problem){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes'
    }).then((result) => {
      if (result.isConfirmed) {        
        this.loaderService.show();       
        this.problemService.deleteProblem(problem.id).subscribe(response => {    
          if(response){         
            this.loaderService.hide();       
            this.alertService.success('Problem deleted successfully');
            this.getProblems();
            }                
          }, error => {
            this.loaderService.hide();       
          });   
      }
    })    
  }

}
