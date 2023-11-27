// import { Component } from '@angular/core';
// import {HttpClient} from "@angular/common/http";
// import {throwError} from "rxjs";
// import {FormBuilder, FormGroup, Validators} from "@angular/forms";
// import {PrintingType} from "../enum/printing-type";
//
// @Component({
//   selector: 'app-print-form',
//   templateUrl: './print-form.component.html',
//   styleUrls: ['./print-form.component.css']
// })
// export class PrintFormComponent {
//   status: "initial" | "uploading" | "success" | "fail" = "initial";
//   uploadForm: FormGroup;
//   file: File | null = null;
//
//   constructor(private http: HttpClient, private formBuilder: FormBuilder) {
//     this.uploadForm = new FormGroup({
//         numberOfCopies: this.formBuilder.control(1, [Validators.required, Validators.min(1)]),
//         printingType: this.formBuilder.control(PrintingType.PRINT_DOUBLE_SIDED_ON_LONG_EDGE, Validators.required),
//         printWholeDocument: this.formBuilder.control(true, Validators.required),
//         pagesRangeFrom: this.formBuilder.control(0),
//         pagesRangeTo: this.formBuilder.control(0),
//     });
//   }
//
//   ngOnInit(): void {}
//
//   onFileSelected(event: any) {
//     const file: File = event.target.files[0];
//
//     if (file) {
//       this.status = "initial";
//       this.file = file;
//     }
//   }
//
//   onUpload() {
//     if (this.file) {
//       const formData = new FormData();
//
//       formData.append('file', this.file, this.file.name);
//
//       const upload$ = this.http.post("https://httpbin.org/post", formData);
//
//       this.status = 'uploading';
//
//       upload$.subscribe({
//         next: () => {
//           this.status = 'success';
//         },
//         error: (error: any) => {
//           this.status = 'fail';
//           return throwError(() => error);
//         },
//       });
//     }
//   }
// }
import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PrintingType} from "../enum/printing-type";
import {throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-print-form',
  templateUrl: './print-form.component.html',
  styleUrls: ['./print-form.component.css']
})
export class PrintFormComponent {
  uploadForm: FormGroup;
  file: File | null = null;
  status: "initial" | "uploading" | "success" | "fail" = "initial";
  printingType = PrintingType;
  enumKeys;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {
    this.enumKeys = Object.keys(this.printingType);

    this.uploadForm = this.formBuilder.group({
      file: [null, Validators.required],
      numberOfCopies: [1, [Validators.required, Validators.min(1)]],
      printingType: [PrintingType.DOUBLE_SIDED_ON_LONG_EDGE.toString, Validators.required],
      printWholeDocument: [true, Validators.required],
      pagesRangeFrom: [{value: 0, disabled: true}],
      pagesRangeTo: [{value: 0, disabled: true}],
    });

    this.uploadForm.get('printWholeDocument')?.valueChanges.subscribe(value => {
      if (value) {
        this.uploadForm.get('pagesRangeFrom')?.disable();
        this.uploadForm.get('pagesRangeTo')?.disable();
      } else {
        this.uploadForm.get('pagesRangeFrom')?.enable();
        this.uploadForm.get('pagesRangeTo')?.enable();
      }
    });
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.status = "initial";
      this.file = file;
      this.uploadForm.patchValue({file: file});
    }
  }

  onUpload() {
    if (this.uploadForm.valid) {
      const upload$ = this.http.post("http://localhost:8080/print", this.uploadForm.value);

      this.status = 'uploading';

      upload$.subscribe({
        next: () => {
          this.status = 'success';
        },
        error: (error: any) => {
          this.status = 'fail';
          return throwError(() => error);
        },
      });
    }
  }

  getValueFromKey(key: string) {
    // @ts-ignore
    return this.printingType[key];
  }

}
