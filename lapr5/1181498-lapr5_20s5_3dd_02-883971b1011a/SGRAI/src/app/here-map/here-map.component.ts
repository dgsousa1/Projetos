import { Component, OnInit, ViewChild, Input, ElementRef } from '@angular/core';
import { LineModel } from '../line.model';
import { LinePathModel } from '../line.path.model';
import { LineService } from '../line.service';
import { NodeModel } from '../node.model';
import { NodeService } from '../node.service';
import { PathCoordsModel } from '../pathcoords.model';
var GeoJSON = require('geojson');

declare var harp: any;

@Component({
  selector: 'here-map',
  templateUrl: './here-map.component.html',
  styleUrls: ['./here-map.component.css']
})
export class HereMapComponent implements OnInit {

  @ViewChild("map", { static: false })
  public mapElement!: ElementRef;

  @Input("token")
  public token!: string;

  @Input("lat")
  public lat!: string;

  @Input("lng")
  public lng!: string;

  public map: any;

  public nodes!: NodeModel[];

  public lines!: LineModel[];

  public paths!: LinePathModel[];

  public coords: PathCoordsModel[] = [];
  public coord!: PathCoordsModel;

  public nodeMatrix: number[][] = [];
  public nodeArray: { lat: Number, lng: Number }[] = [];

  public constructor(private nodeService: NodeService, private lineService: LineService) { }

  public ngOnInit() {
    this.getNodes('name');
    this.getCoords('name');
  }

  getCoords(order: any): void {
    this.lineService.getLines(order).subscribe(lines => {
      this.lines = lines;

      for (var i = 0; i < this.lines.length; i++) {
        this.lineService.getCoordsFromLine(this.lines[i].lineId).subscribe(coord => {
          this.coords.push(coord);
        })
      }
    })
  }

  getNodes(order: any): void {
    this.nodeService.getNodes(order)
      .subscribe(nodes => {
        this.nodes = nodes;
      });
  }

  loadNodes(): any[] {
    var ar = new Array();
    for (var i = 0; i < this.nodes.length; i++) {
      var json = {
        lat: this.nodes[i].latitude,
        lng: this.nodes[i].longitude
      }
      ar.push(json);
      this.nodeMatrix[i] = [0];
      this.nodeArray[i] = json;
      for (var j = 0; j < this.nodes.length; j++) {
        this.nodeMatrix[i][j] = 0;
      }
    }
    return ar;
  }

  getIndexFromCoordenates(nodeCoords: any): number {
    var index = this.nodeArray.findIndex(function (node, i) {
      return node.lat === nodeCoords.lat && node.lng === nodeCoords.lng;
    });
    return index.valueOf();
  }

  loadLines(): void {
    var line = new Array();
    var coor;
    var goPath;
    var goCoord = new Array();
    var returnPath;
    var returnCoord = new Array();
    var l;
    var lineCoord;
    for (var a = 0; a < this.coords.length; a++) {
      line = new Array();
      coor = this.coords[a];
      for (var b = 0; b < coor.goPaths.length; b++) {
        goPath = coor.goPaths[b];

        var lati = parseFloat(goPath.lan1);
        var lngi = parseFloat(goPath.lng1);
        var noOCoords = { lat: lati, lng: lngi };
        var i = this.getIndexFromCoordenates(noOCoords);
        var latj = parseFloat(goPath.lan2);
        var lngj = parseFloat(goPath.lng2);
        var noDCoords = { lat: latj, lng: lngj };
        var j = this.getIndexFromCoordenates(noDCoords);
        if (i > j) {
          var t = i;
          i = j;
          j = t;
        }
        var r = Math.sqrt(Math.pow(lngj - lngi, 2) + Math.pow(latj - lati, 2));
        var sinBeta = -(lngj - lngi) / r;
        var cosBeta = (latj - lati) / r;
        var n = this.nodeMatrix[i][j];
        var d = 0.0005;
        goCoord = new Array();
        goCoord.push((lngi + n * d * cosBeta).toString());
        goCoord.push((lati + n * d * sinBeta).toString());
        line.push(goCoord);
        goCoord = new Array();
        goCoord.push((lngj + n * d * cosBeta).toString());
        goCoord.push((latj + n * d * sinBeta).toString());
        line.push(goCoord);
        //console.log(line);
        l = { line: line };
        lineCoord = new Array();
        lineCoord.push(l);
        this.dropLines(goPath.key + "-" + b, this.rgbToHex(coor.color), lineCoord);
        line = new Array();

        if (n <= 0) {
          this.nodeMatrix[i][j] = 1 - n;
        } else {
          this.nodeMatrix[i][j] = -n;
        }
      }
/* COMO OS CAMINHOS SÃO BI-DIRECIONAIS NÃO HÁ NECESSIDADE DE REPRESENTAR OS DE RETORNO
      for (var b = 0; b < coor.returnPaths.length; b++) {
        returnPath = coor.returnPaths[b];

        var lati = parseFloat(returnPath.lan1);
        var lngi = parseFloat(returnPath.lng1);
        var noOCoords = { lat: lati, lng: lngi };
        var i = this.getIndexFromCoordenates(noOCoords);
        var latj = parseFloat(returnPath.lan2);
        var lngj = parseFloat(returnPath.lng2);
        var noDCoords = { lat: latj, lng: lngj };
        var j = this.getIndexFromCoordenates(noDCoords);
        if (i > j) {
          var t = i;
          i = j;
          j = t;
        }
        var r = Math.sqrt(Math.pow(lngj - lngi, 2) + Math.pow(latj - lati, 2));
        var sinBeta = -(lngj - lngi) / r;
        var cosBeta = (latj - lati) / r;
        var n = this.nodeMatrix[i][j];
        var d = 0.0005;
        returnCoord = new Array();
        returnCoord.push((lngi + n * d * cosBeta).toString());
        returnCoord.push((lati + n * d * sinBeta).toString());
        line.push(returnCoord);
        returnCoord = new Array();
        returnCoord.push((lngj + n * d * cosBeta).toString());
        returnCoord.push((latj + n * d * sinBeta).toString());
        line.push(returnCoord);
        //console.log(line);
        l = { line: line };
        lineCoord = new Array();
        lineCoord.push(l);
        this.dropLines(returnPath.key + "-" + b, this.rgbToHex(coor.color), lineCoord);
        line = new Array();

        if (n <= 0) {
          this.nodeMatrix[i][j] = 1 - n;
        } else {
          this.nodeMatrix[i][j] = -n;
        }
      }
      */
    }
  }


  rgbToHex(input: string): string {
    var rgb = input.split(',');
    var r = parseInt(rgb[0].substring(4));
    var g = parseInt(rgb[1]);
    var b = parseInt(rgb[2]);
    return this.rgb_to_hex(r, g, b);
  }

  //https://stackoverflow.com/questions/5623838/rgb-to-hex-and-hex-to-rgb
  rgb_to_hex(red: number, green: number, blue: number) {
    const rgb = (red << 16) | (green << 8) | (blue << 0);
    return '#' + (0x1000000 + rgb).toString(16).slice(1);
  }

  loadGraph(): void {
    this.getNodes('name');
    this.getCoords('name');
    this.dropPoints("initial-nodes", this.loadNodes());
    this.loadLines();
  }


  public ngAfterViewInit() {
    this.map = new harp.MapView({
      canvas: this.mapElement.nativeElement,
      theme: "https://unpkg.com/@here/harp-map-theme@latest/resources/berlin_tilezen_effects_streets.json",
    });


    const controls = new harp.MapControls(this.map);
    const omvDataSource = new harp.OmvDataSource({
      baseUrl: "https://vector.hereapi.com/v2/vectortiles/base/mc",
      apiFormat: harp.APIFormat.XYZOMV,
      styleSetName: "tilezen",
      authenticationCode: "gUwWxT_fPQqCnIT8TsrTGUnfPNS3ZxQCAs3hkHSFU6c",
      authenticationMethod: {
        method: harp.AuthenticationMethod.QueryString,
        name: "apikey"
      }
    });
    this.map.addDataSource(omvDataSource);
    this.map.setCameraGeolocationAndZoom(new harp.GeoCoordinates(Number(this.lat), Number(this.lng)), 12);

    //this.dropPoints("example-points", [{ lat: 41.1293363229325, lng: -8.4464785432391 }, { lat: 41.1937898023744, lng: -8.38716802227697 }]);
  }

  public createPoints(positions: any[]): any {
    return GeoJSON.parse(positions, { Point: ["lat", "lng"] });
  }

  public createLines(positions: any[]): any {
    var t = GeoJSON.parse(positions, { LineString: 'line' });
    return t;
  }

  public dropPoints(name: string, positions: any[]) {
    const geoJsonDataProvider = new harp.GeoJsonDataProvider(name, this.createPoints(positions));
    const geoJsonDataSource = new harp.OmvDataSource({
      dataProvider: geoJsonDataProvider,
      name: name
    });
    this.map.addDataSource(geoJsonDataSource).then(() => {
      const styles = [{
        when: "$geometryType == 'point'",
        technique: "circles",
        renderOrder: 100000,
        attr: {
          color: "#7ED321",
          size: 20
        }
      }];
      geoJsonDataSource.setStyleSet(styles);
      this.map.update();
    });
  }

  public dropLines(name: string, color: string, positions: any[]) {
    const geoJsonDataProvider = new harp.GeoJsonDataProvider(name, this.createLines(positions));
    const geoJsonDataSource = new harp.OmvDataSource({
      dataProvider: geoJsonDataProvider,
      name: name
    });

    this.map.addDataSource(geoJsonDataSource).then(() => {
      const styles = [{
        "when": ["==", ["geometry-type"], "LineString"],
        "renderOrder": 5000,
        "technique": "solid-line",
        "color": color,
        "opacity": 1,
        "metricUnit": "Pixel",
        "lineWidth": 5
      }]

      geoJsonDataSource.setStyleSet(styles);
      this.map.update();
    });
  }
}