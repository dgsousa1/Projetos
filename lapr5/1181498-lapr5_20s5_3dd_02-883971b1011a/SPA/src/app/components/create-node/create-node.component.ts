import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { NodeService } from '../../services/node.service';

@Component({
  selector: 'app-create-node',
  templateUrl: './create-node.component.html',
  styleUrls: ['./create-node.component.css']
})
export class CreateNodeComponent implements OnInit {
  public userForm !: FormGroup;
  public message!: string;
  public errorOccur: boolean = false;

  constructor(private fb: FormBuilder,
    private nodeService: NodeService,
    private flashMessageService: FlashMessagesService,
    private router: Router) { }

  ngOnInit(): void {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      shortName: ['', Validators.required],
      latitude: ['', [Validators.maxLength(32), Validators.min(-90), Validators.max(90)]],
      longitude: ['', [Validators.maxLength(32), Validators.min(-180), Validators.max(180)]],
      isDepot: [''],
      isReliefPoint: [''],
    })

    this.userForm.get('isDepot')?.setValue(false);
    this.userForm.get('isReliefPoint')?.setValue(false);
  }
  addNode() {
    this.nodeService.addNode(this.userForm.value).subscribe((response: any) => {
      console.log(response);
      this.flashMessageService.show('Nó criado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
      this.router.navigate(['/dashboard']);
    },
      (error) => {
        this.flashMessageService.show('Erro! Nome de nó já existe. Tente novamente', { cssClass: 'alert-danger', timeout: 3000 });
        this.userForm.reset();
        this.userForm.get('isDepot')?.setValue(false);
        this.userForm.get('isReliefPoint')?.setValue(false);
        this.router.navigate(['/create-node']);
      });
  }
}