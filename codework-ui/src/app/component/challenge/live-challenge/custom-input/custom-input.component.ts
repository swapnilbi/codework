import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
  styleUrls: ['./custom-input.component.scss']
})
export class CustomInputComponent {

  public onClose: Subject<string>;
  public input?:string;

  constructor(private _bsModalRef: BsModalRef) { 
    this.onClose = new Subject();
  }

  public onConfirm(): void {
      this.onClose.next(this.input);
      this._bsModalRef.hide();
  }

  public onCancel(): void {      
      this._bsModalRef.hide();
  }

}
