import { Component, OnInit } from '@angular/core';
import { NodeModel } from '../../models/node.model';
import { NodeService } from '../../services/node.service';

@Component({
  selector: 'app-list-nodes',
  templateUrl: './list-nodes.component.html',
  styleUrls: ['./list-nodes.component.css']
})
export class ListNodesComponent implements OnInit {
  nodes!: NodeModel[];
  exactNode!: NodeModel;

  orderType: string = 'name'
  nameOrID: string | undefined;

  input: string = '';

  depot: boolean = false;
  reliefPoint: boolean = false;

  public message!: string;
  public errorOccur: boolean = false;
  public loaded: boolean = false;

  constructor(private nodeService: NodeService) {
  }

  ngOnInit(): void {
  }

  getNodes(order: any): void {
    this.nodeService.getNodes(order)
      .subscribe(nodes => {
        this.nodes = nodes;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        }
      );

      this.loaded = true;
  }

  getNodesByName(nodeName: string, order: string): void {
    this.nodeService.getNodesByName(nodeName, order)
      .subscribe(nodes => {
        this.nodes = nodes;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        });
  }

  getDepots(order: any): void {
    this.nodeService.getNodesByDepot(order)
      .subscribe(nodes => {
        this.nodes = nodes;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        });
  }

  getReliefPoints(order: any): void {
    this.nodeService.getNodesByReliefPoints(order)
      .subscribe(nodes => {
        this.nodes = nodes;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        });
  }

  getNodesById(id: any): void {
    this.nodes = [];
    this.nodeService.getNodesById(id)
      .subscribe(exactNode => {
        this.nodes[0] = exactNode;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        });
  }

  getMultipleFilters(nodeName: string, depot: boolean, reliefPoint: boolean, order: string): void {
    this.nodeService.getMultipleFilters(nodeName, depot, reliefPoint, order)
      .subscribe(nodes => {
        this.nodes = nodes;
      },
        (error) => {
          this.message = error;
          this.errorOccur = true;
        },
        () => {
          this.errorOccur = false;
        });
  }

  searchNodeList(): void {

    //filter is empty
    if (this.nameOrID == undefined) {
      this.getNodes(this.orderType);
    }

    if (this.nameOrID == 'name' && this.input == '') {
      if (this.depot == true) {
        this.getDepots(this.orderType);
      } else if (this.reliefPoint == true) {
        this.getReliefPoints(this.orderType);
      }
    }

    if (this.nameOrID == 'name' && this.input != '') {
      if (this.depot == false && this.reliefPoint == false) {
        this.getNodesByName(this.input, this.orderType);
      } else if (this.reliefPoint == true || this.depot == true) {
        this.getMultipleFilters(this.input, this.depot, this.reliefPoint, this.orderType);
      }
    }

    if (this.nameOrID == 'id') {
      this.getNodesById(this.input);
    }
  }

  clearInputs(): void {
    this.orderType = 'name';
    this.nameOrID = undefined;
    this.input = '';
    this.depot = false;
    this.reliefPoint = false;
    this.getNodes(this.orderType);
  }
}

