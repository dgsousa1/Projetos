import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LineModel } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import { NodeModel } from '../../models/node.model';
import { NodeService } from '../../services/node.service';
import { PathModel } from '../../models/path.model';
import { PathService } from '../../services/path.service';
@Component({
  selector: 'app-list-paths',
  templateUrl: './list-paths.component.html',
  styleUrls: ['./list-paths.component.css']
})
export class ListPathsComponent implements OnInit {

  singleLine!: any;
  pathsGo: PathModel[] = [];
  pathsReturn: PathModel[] = [];
  nodeX!: NodeModel;

  input!: string;
  loaded: boolean = false;

  constructor(private lineService: LineService,
    private pathService: PathService) { }

  ngOnInit(): void {
  }

  getExactLine(name: string): void {
    this.pathsGo = new Array<PathModel>();
    this.pathsReturn = new Array<PathModel>();
    
    this.loaded = true;
    this.lineService.getPathsFromExactLineName(this.input)
      .subscribe(data => {
        this.singleLine = data;
        //iterate through go paths inside respective line
        for (let i = 0; i < this.singleLine.goPaths.length; i++) {

          this.pathService.getPathByKeyWithNodesName(this.singleLine.goPaths[i].key)
            .subscribe(res => {
              console.log(res.pathNodes.length);

              console.log('inside path subs: ' + JSON.stringify(res))
              this.pathsGo.push(res);
            })
        }

        for (let i = 0; i < this.singleLine.returnPaths.length; i++) {
          this.pathService.getPathByKeyWithNodesName(this.singleLine.returnPaths[i].key)
            .subscribe(res => {
              console.log(res.pathNodes.length);

              console.log('inside path subs: ' + JSON.stringify(res))
              this.pathsReturn.push(res);
            })
        }

        //console.log(this.singleLine.id);
        //console.log(this.paths);
      });
    //console.log('LENGHT VVVV')
    //console.log(this.paths.length);
    //this.nodeService.getNodesById()


  }
}
