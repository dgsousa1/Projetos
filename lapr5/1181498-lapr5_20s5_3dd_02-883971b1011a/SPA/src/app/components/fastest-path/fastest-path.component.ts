import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FastestPathModel } from '../../models/fastest-path.model';
import { FastestPathService } from '../../services/fastest-path.service';
import { FormatedFastestPathModel } from '../../models/formated-fastest-path.model';
import { NodeService } from 'src/app/services/node.service';
import { NodeModel } from 'src/app/models/node.model';

@Component({
  selector: 'app-fastest-path',
  templateUrl: './fastest-path.component.html',
  styleUrls: ['./fastest-path.component.css']
})
export class FastestPathComponent implements OnInit {
  public fp!: FastestPathModel;
  public ffp!: FormatedFastestPathModel;
  public fpForm !: FormGroup;
  public x: boolean = false;
  public nodes!: NodeModel[];

  constructor(private fb: FormBuilder,
    private fastestPathService: FastestPathService,
    private nodeService : NodeService) { }

  ngOnInit(): void {
    this.getNodes();
    this.fpForm = this.fb.group({
      inode: ['', Validators.required],
      fnode: ['', Validators.required],
      time: ['', Validators.required]
    })
  }

  getNodes() : void{
    this.nodeService.getNodes('name')
      .subscribe(nodes =>{
        this.nodes = nodes;
      })
  }

  getData(): void {
    this.fastestPathService.getData(this.fpForm.get('inode')?.value,
      this.fpForm.get('fnode')?.value, this.fpForm.get('time')?.value)
      .subscribe(res => {
        //this.ffp.custo = res.custo;

        var caminho = res.cam;
        //[('ESTPA','PARED',38),('PARED','LORDL',11),('LORDL','ESTLO',35)]

        var f = caminho.slice(2, caminho.length - 2);
        var fcaminho = f.split('),(');
        var camProps = new Array();

        for (let index = 0; index < fcaminho.length; index++) {
          var element = fcaminho[index];
          var elements = element.split(',');
          var props = {
            inode: elements[0].slice(1, elements[0].length - 1),
            fnode: elements[1].slice(1, elements[1].length - 1),
            line: elements[2]
          };

          camProps.push(props);
        }

        this.ffp = {
          custo: res.custo,
          cam: camProps
        }

        this.x = true;
      })

  }

}
