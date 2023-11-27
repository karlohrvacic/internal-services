import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PrintFormComponent} from "./print-form/print-form.component";

const routes: Routes = [
    {path: '', component: PrintFormComponent},
    {path: 'print', component: PrintFormComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
