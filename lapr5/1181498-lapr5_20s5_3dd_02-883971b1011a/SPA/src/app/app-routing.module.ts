import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlgoritmoAlgavComponent } from './components/algoritmo-algav/algoritmo-algav.component';
import { CreateDriverTypeComponent } from './components/create-driver-type/create-driver-type.component';
import { CreateLineComponent } from './components/create-line/create-line.component';
import { CreateNodeComponent } from './components/create-node/create-node.component';
import { CreatePathComponent } from './components/create-path/create-path.component';
import { CreateTripComponent } from './components/create-trip/create-trip.component';
import { CreateVehicleTypeComponent } from './components/create-vehicle-type/create-vehicle-type.component';
import { CriarServicoTripulanteComponent } from './components/criar-servico-tripulante/criar-servico-tripulante.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DriverComponent } from './components/driver/driver.component';
import { FastestPathComponent } from './components/fastest-path/fastest-path.component';
import { GenerateTripsComponent } from './components/generate-trips/generate-trips.component';
import { ImportFileComponent } from './components/import-file/import-file.component';
import { ListDriverTypesComponent } from './components/list-driver-types/list-driver-types.component';
import { ListLinesComponent } from './components/list-lines/list-lines.component';
import { ListNodesComponent } from './components/list-nodes/list-nodes.component';
import { ListPathsComponent } from './components/list-paths/list-paths.component';
import { ListTripsOfLineComponent } from './components/list-trips-of-line/list-trips-of-line.component';
import { ListVehicleTypesComponent } from './components/list-vehicle-types/list-vehicle-types.component';
import { ListarServicoTripulanteComponent } from './components/listar-servico-tripulante/listar-servico-tripulante.component';
import { ListarServicoViaturaComponent } from './components/listar-servico-viatura/listar-servico-viatura.component';
import { LoginComponent } from './components/login/login.component';
import { MapaComponent } from './components/mapa/mapa.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';
import { ServiceDutyComponent } from './components/service-duty/service-duty.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { WorkBlockComponent } from './components/work-block/work-block.component';
import { ImportFileMdvComponent } from './components/import-file-mdv/import-file-mdv.component';

import { RoleGuard } from './guard/role.guard';
import { LoggedInGuard } from './guard/logged-in.guard';
import { AuthGuard } from './guard/auth.guard';
import { GerarTodosServicosTripulantesComponent } from './components/gerar-todos-servicos-tripulantes/gerar-todos-servicos-tripulantes.component';

const routes: Routes = [
  { path: "navbar", component: NavbarComponent },
  //MASTER DATA REDE
  {
    path: 'import-file',
    component: ImportFileComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },

  {
    path: 'create-node',
    component: CreateNodeComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },

  {
    path: 'list-nodes',
    component: ListNodesComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'create-line',
    component: CreateLineComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'list-lines',
    component: ListLinesComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'create-driver-type',
    component: CreateDriverTypeComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'list-driver-types',
    component: ListDriverTypesComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'create-vehicle-type',
    component: CreateVehicleTypeComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'list-vehicle-types',
    component: ListVehicleTypesComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'create-path',
    component: CreatePathComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'list-paths',
    component: ListPathsComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: 'fastest-path',
    component: FastestPathComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  //MASTER DATA VIAGEM
  {
    path: "login",
    component: LoginComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: "profile",
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "dashboard",
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "create-trip",
    component: CreateTripComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "driver",
    component: DriverComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "vehicle",
    component: VehicleComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "service-duty",
    component: ServiceDutyComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "work-block",
    component: WorkBlockComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "generate-trips",
    component: GenerateTripsComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "list-trips-of-line",
    component: ListTripsOfLineComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "mapa",
    component: MapaComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "algoritmo-algav",
    component: AlgoritmoAlgavComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "servico-tripulante",
    component: CriarServicoTripulanteComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "list-vehicle-duties",
    component: ListarServicoViaturaComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "list-driver-duties",
    component: ListarServicoTripulanteComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "import-file-mdv",
    component: ImportFileMdvComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  },
  {
    path: "generate-all-driver-duties",
    component: GerarTodosServicosTripulantesComponent,
    canActivate: [RoleGuard],
    data: ['Adminstrador']
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [RoleGuard, LoggedInGuard, AuthGuard]
})
export class AppRoutingModule { }
