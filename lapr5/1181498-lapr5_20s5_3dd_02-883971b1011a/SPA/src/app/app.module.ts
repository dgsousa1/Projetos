import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FlashMessagesModule } from 'angular2-flash-messages';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';

import { JwtModule } from "@auth0/angular-jwt";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ImportFileComponent } from './components/import-file/import-file.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateNodeComponent } from './components/create-node/create-node.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListNodesComponent } from './components/list-nodes/list-nodes.component';
import { CreateLineComponent } from './components/create-line/create-line.component';
import { ListLinesComponent } from './components/list-lines/list-lines.component';
import { CreateDriverTypeComponent } from './components/create-driver-type/create-driver-type.component';
import { ListDriverTypesComponent } from './components/list-driver-types/list-driver-types.component';
import { CreateVehicleTypeComponent } from './components/create-vehicle-type/create-vehicle-type.component';
import { ListVehicleTypesComponent } from './components/list-vehicle-types/list-vehicle-types.component';
import { CreatePathComponent } from './components/create-path/create-path.component';
import { ListPathsComponent } from './components/list-paths/list-paths.component';
import { FastestPathComponent } from './components/fastest-path/fastest-path.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CreateTripComponent } from './components/create-trip/create-trip.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DriverComponent } from './components/driver/driver.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { ServiceDutyComponent } from './components/service-duty/service-duty.component';
import { WorkBlockComponent } from './components/work-block/work-block.component';
import { GenerateTripsComponent } from './components/generate-trips/generate-trips.component';
import { ListTripsOfLineComponent } from './components/list-trips-of-line/list-trips-of-line.component';
import { MapaComponent } from './components/mapa/mapa.component';
import { CommonModule } from '@angular/common';
import { AlgoritmoAlgavComponent } from './components/algoritmo-algav/algoritmo-algav.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CriarServicoTripulanteComponent } from './components/criar-servico-tripulante/criar-servico-tripulante.component';
import { ListarServicoViaturaComponent } from './components/listar-servico-viatura/listar-servico-viatura.component';
import { ListarServicoTripulanteComponent } from './components/listar-servico-tripulante/listar-servico-tripulante.component';
import { ImportFileMdvComponent } from './components/import-file-mdv/import-file-mdv.component';
import { GerarTodosServicosTripulantesComponent } from './components/gerar-todos-servicos-tripulantes/gerar-todos-servicos-tripulantes.component';

export function tokenGetter() {
  return localStorage.getItem("access_token");
}

@NgModule({
  declarations: [
    AppComponent,
    ImportFileComponent,
    CreateNodeComponent,
    ListNodesComponent,
    CreateLineComponent,
    ListLinesComponent,
    CreateDriverTypeComponent,
    ListDriverTypesComponent,
    CreateVehicleTypeComponent,
    ListVehicleTypesComponent,
    CreatePathComponent,
    ListPathsComponent,
    FastestPathComponent,
    NavbarComponent,
    CreateTripComponent,
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    DriverComponent,
    VehicleComponent,
    ServiceDutyComponent,
    WorkBlockComponent,
    GenerateTripsComponent,
    ListTripsOfLineComponent,
    MapaComponent,
    AlgoritmoAlgavComponent,
    ProfileComponent,
    CriarServicoTripulanteComponent,
    ListarServicoViaturaComponent,
    ListarServicoTripulanteComponent,
    ImportFileMdvComponent,
    GerarTodosServicosTripulantesComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlashMessagesModule.forRoot(),
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter
      },
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
