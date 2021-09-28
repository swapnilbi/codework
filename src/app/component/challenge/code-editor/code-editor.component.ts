import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'code-editor',
  templateUrl: './code-editor.component.html',
  styleUrls: ['./code-editor.component.scss']
})
export class CodeEditorComponent implements OnInit {

  editorOptions = {
    language: 'java'
  };  

  language: string;

  languages = [
        { id: 'C', description: 'C (gcc 5.4.0)' },
        { id: 'CPP', description: 'C++ (g++ 5.4.0)' },
        { id: 'JAVA', description: 'Java (openjdk 1.7.0_95)' },
        { id: 'JAVA8', description: 'Java 14 (oracle 14)' }
    ];
  
  loader = true;
  code: string= 'class Simple{\n\n  public static void main(String args[]){\n \t System.out.println("Hello Java"); \n  } \n\n }';

  constructor() {
    this.language = 'JAVA';
   }

  ngOnInit(): void {
  }

}
