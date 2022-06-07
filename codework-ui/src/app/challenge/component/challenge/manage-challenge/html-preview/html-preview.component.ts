import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-html-preview',
  templateUrl: './html-preview.component.html',
  styleUrls: ['./html-preview.component.scss']
})
export class HtmlPreviewComponent implements OnInit {

  code? : string;

  constructor(private _bsModalRef: BsModalRef) { 
    
  }

  ngOnInit(): void {
  }

  public onCancel(): void {      
    this._bsModalRef.hide();
  }
}

