import { Component, OnInit } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'app-ag-grid-row-number',
  templateUrl: './ag-grid-row-number.component.html'
})
export class AgGridRowNumberComponent implements ICellRendererAngularComp {

  rowNumber: number= 0;
  refresh(params: any): boolean {
    return true;
  }

  agInit(params: import('ag-grid-community').ICellRendererParams): void {    
    this.rowNumber = params.rowIndex + 1;
  }
  afterGuiAttached?(params?: import('ag-grid-community').IAfterGuiAttachedParams): void {

  }

  constructor() { }
}
