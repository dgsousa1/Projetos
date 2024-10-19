import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { ImportFileService } from '../../services/import-file.service';

@Component({
  selector: 'app-import-file',
  templateUrl: './import-file.component.html',
  styleUrls: ['./import-file.component.css']
})
export class ImportFileComponent implements OnInit {
  public message!: string;
  public errorOccur: boolean = false;
  public loading = "";

  constructor(private importFileService: ImportFileService,
    private flashMessageService: FlashMessagesService,
    private router: Router) { }

  ngOnInit(): void {
  }

  importFile() {
    this.loading = "Importar Dados..."
    this.importFileService.importFile()
      .subscribe((response: any) => {
        this.loading = "";
        this.flashMessageService.show('Dados importados com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
      },
        (error) => {
          this.loading = "";
          this.flashMessageService.show('Ocorreu erros durante a importação dos dados!', { cssClass: 'alert-danger', timeout: 3000 });
        });
  }
}
