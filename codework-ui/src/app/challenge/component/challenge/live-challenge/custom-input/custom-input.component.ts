import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html'
})
export class CustomInputComponent {

  public onSubmit: Subject<string>;
  public input?:string;

  constructor(private _bsModalRef: BsModalRef) { 
    this.onSubmit = new Subject();
  }

  public onConfirm(): void {
      this.onSubmit.next(this.input);
      this._bsModalRef.hide();
  }

  public onCancel(): void {      
      this._bsModalRef.hide();
  }

}
