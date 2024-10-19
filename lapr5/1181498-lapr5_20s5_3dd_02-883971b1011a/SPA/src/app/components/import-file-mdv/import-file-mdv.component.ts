import { Component, OnInit } from '@angular/core';
import { ImportFileMdvService } from 'src/app/services/import-file-mdv.service';

@Component({
  selector: 'app-import-file-mdv',
  templateUrl: './import-file-mdv.component.html',
  styleUrls: ['./import-file-mdv.component.css']
})
export class ImportFileMdvComponent implements OnInit {
  public message!: string;
  public errorOccur: boolean = false;
  public loading = "";

  constructor(private importFileMdvService: ImportFileMdvService) { }

  ngOnInit(): void {
  }

  importFile() {
    this.loading = "Importing Data.."
    this.importFileMdvService.importFile().subscribe((response: any) => {
      this.loading = "";
      console.log(response);
    },
      (error) => {
        this.loading = "";
        this.message = error;
        this.errorOccur = true;
      },
      () => {
        this.loading = "";
        this.message = 'Domínio importado com sucesso!';
        this.errorOccur = false;
      });

    this.resetInputs();
  }
  resetInputs(): void {
    setTimeout(() => {
      if (this.message != '') this.message = '';
    }, 3000);
  }

}
