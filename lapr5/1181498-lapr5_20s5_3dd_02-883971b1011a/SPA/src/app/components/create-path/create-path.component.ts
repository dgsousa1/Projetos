import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NodeModel } from '../../models/node.model';
import { NodeService } from '../../services/node.service';
import { PathModel } from '../../models/path.model';
import { PathService } from '../../services/path.service';

@Component({
  selector: 'app-create-path',
  templateUrl: './create-path.component.html',
  styleUrls: ['./create-path.component.css']
})
export class CreatePathComponent implements OnInit {

  public form !: FormGroup;
  public pathNodeList!: FormArray;
  public nodesList!: NodeModel[];
  public x!: FormGroup;
  public path!: PathModel;


  public message!: string;
  public errorOccur: boolean = false;


  get pathNodeFromGroup() {
    return this.form.get('pathNodes') as FormArray;
  }

  constructor(private fb: FormBuilder,
    private pathService: PathService,
    private nodeService: NodeService) { }

  ngOnInit(): void {
    this.getNodes('name');
    /*Criar um form maior que contem forms pathNode */
    this.form = this.fb.group({
      key: ['', Validators.required],
      pathNodes: this.fb.array([this.createPathNode()])
    });
    this.pathNodeList = this.form.get('pathNodes') as FormArray;
  }

  getNodes(order: any): void {
    this.nodeService.getNodes(order)
      .subscribe(nodesList => {
        this.nodesList = nodesList;
        console.log(nodesList)
      });
  }

  /*Criar um form group para pathNode */
  createPathNode(): FormGroup {
    this.x = this.fb.group({
      duracao: ['', Validators.required],
      distancia: ['', Validators.required],
      inicialNode: (['', Validators.required]),
      finalNode: ['', Validators.required]
    });
    return this.x;
  }

  addPathNodeForm() {
    this.pathNodeList.push(this.createPathNode());
  }

  removePathNodeForm(index: number) {
    if (this.pathNodeList.length > 1) {
      this.pathNodeList.removeAt(index);
    }
  }

  getPathNodesFromGroup(index: number): FormGroup {
    const formGroup = this.pathNodeList.controls[index] as FormGroup;
    return formGroup;
  }

  onSubmit(data: any) {
    //transfering the data received from the form to the pathNode to manipulate and change name to id 
    this.path = this.form.value;
    var pathNodes = this.path.pathNodes;
    for (let i = 0; i < pathNodes.length; i++) {
      //recolhe os 2 nodes name para vars
      var inicialNode = pathNodes[i].inicialNode;
      var finalNode = pathNodes[i].finalNode;
      var timeToSeg = pathNodes[i].duracao*60;
      pathNodes[i].duracao =  timeToSeg;

      for (let j = 0; j < this.nodesList.length; j++) {
        if (inicialNode == this.nodesList[j].name) {
          pathNodes[i].inicialNode = this.nodesList[j].nodeId;
        }
        if (finalNode == this.nodesList[j].name) {
          pathNodes[i].finalNode = this.nodesList[j].nodeId;
        }
      }
    }

    console.log(this.path);

    this.pathService.addPath(this.path).subscribe((response: any) => {
      console.log(response);
    },
      (error) => {
        this.message = JSON.stringify(error);
        this.errorOccur = true;
      },
      () => {
        this.message = 'Congratulations! Path successfully created';
        this.errorOccur = false;
      });
    this.resetUserForm();
  }

  resetUserForm(): void {
    setTimeout(() => {
      if (this.message != '') this.message = '';
    }, 10000);

    this.form.reset();
  }
}
