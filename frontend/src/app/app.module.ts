import { BrowserModule, DomSanitizer } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { SubjectLoadTableComponent } from './load-distribution/subject-load-table/subject-load-table.component';
import { OtherLoadTableComponent } from './load-distribution/other-load-table/other-load-table.component';
import { LoadDistributorComponent } from './load-distribution/load-distributor/load-distributor.component';
import { AppRoutingModule } from './app-routing.module';
import { StatisticsComponent } from './load-distribution/statistics/statistics.component';
import { ChartsModule } from 'ng2-charts';
import { DataCrudModule } from "./data-crud/data-crud.module";
import { SystemModule } from "./system/system.module";

@NgModule({
  declarations: [
    AppComponent,
    SubjectLoadTableComponent,
    OtherLoadTableComponent,
    LoadDistributorComponent,
    StatisticsComponent
  ],
  imports: [
    BrowserModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatRadioModule,
    FormsModule,
    MatSelectModule,
    MatIconModule,
    HttpClientModule,
    AppRoutingModule,
    ChartsModule,
    ReactiveFormsModule,
    DataCrudModule,
    SystemModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(matIconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    matIconRegistry.addSvgIconSet(domSanitizer.bypassSecurityTrustResourceUrl('./assets/delete.svg'));
  }
}
