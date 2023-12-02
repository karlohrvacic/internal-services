import {Component} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {PrintingType} from "../enum/printing-type";
import {throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-print-form',
  templateUrl: './print-form.component.html',
  styleUrls: ['./print-form.component.css']
})
export class PrintFormComponent {
  uploadForm: UntypedFormGroup;
  file: File | null = null;
  status: "initial" | "uploading" | "success" | "fail" = "initial";
  printingType = PrintingType;
  enumKeys;
  private BACKEND_PRINT_URL: string = "http://localhost:8080/print";

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.enumKeys = Object.keys(this.printingType);

    this.uploadForm = new UntypedFormGroup({
      'file': new UntypedFormControl(null, [Validators.required]),
      'numberOfCopies': new UntypedFormControl(1, [Validators.required]),
      'printingType': new UntypedFormControl("default", [Validators.required]),
      'printWholeDocument': new UntypedFormControl(true, [Validators.required]),
      'pagesRangeFrom': new UntypedFormControl(0, [Validators.required]),
      'pagesRangeTo': new UntypedFormControl(0, [Validators.required]),
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
    if (this.uploadForm.valid && this.file) {
      const formData = new FormData();

      formData.append('file', this.file, this.file.name);

      const upload$ = this.http.post(this.createRequestParam(), formData, {responseType: 'text'});

      this.status = 'uploading';

      upload$.subscribe({
        next: (res: any) => {
          this.status = 'success';
          this.toastr.info(res, 'Job id');
        },
        error: (error: any) => {
          this.status = 'fail';
          this.toastr.error(error.message, 'Error while printing');
          return throwError(() => error);
        }
      });
    }
  }

  getValueFromKey(key: string) {
    // @ts-ignore
    return this.printingType[key];
  }

  private createRequestParam() {
    return this.BACKEND_PRINT_URL +
        "?copies=" + this.uploadForm.get('numberOfCopies')?.value +
        "&printType=" + this.uploadForm.get('printingType')?.value +
        "&printWholeDocument=" + this.uploadForm.get('printWholeDocument')?.value +
        "&startPage=" + this.uploadForm.get('pagesRangeFrom')?.value +
        "&endPage=" + this.uploadForm.get('pagesRangeTo')?.value;
  }

}
