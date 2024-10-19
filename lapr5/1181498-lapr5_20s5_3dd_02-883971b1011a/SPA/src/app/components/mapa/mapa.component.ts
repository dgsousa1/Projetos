import { AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import * as THREE from 'three';

import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';

import * as Mapboxgl from 'mapbox-gl';
import { environment } from 'src/environments/environment.prod';
import { LineService } from 'src/app/services/line.service';
import { NodeService } from 'src/app/services/node.service';
import { LineModel } from 'src/app/models/line.model';
import { PathCoordsModel } from 'src/app/models/pathcoords.model';
import { NodeModel } from 'src/app/models/node.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TemplateRef } from '@angular/core';

declare global {
  interface Window {
    tb: any;
  }
}
var map: Mapboxgl.Map;
var listaIsDepot: { lat: number, lng: number }[] = [];
var someValue: number = 0;
var changedLight: boolean = false;

var busLong: number = -8.334116;
var busLat: number = 41.205468;
var rotateBus: number = 0;

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})

export class MapaComponent implements OnInit {

  public layer !: Mapboxgl.AnyLayer;
  private buildingLayerToggle = false;

  public lines!: LineModel[];
  public coords: PathCoordsModel[] = [];
  public nodes!: NodeModel[];

  public nodeMatrix: number[][] = [];
  public nodeArray: { name: string, lat: number, lng: number }[] = [];
  public pathLayers: { name: string, pathKey: string, ite: number }[] = [];

  private camera: THREE.Camera = new THREE.PerspectiveCamera;
  private scene: THREE.Scene = new THREE.Scene;
  private loader !: GLTFLoader;
  private renderer !: THREE.WebGLRenderer;
  private matrix !: any;

  modalRef!: BsModalRef;

  public isLoaded2D: boolean = false;
  public isLoaded3D: boolean = false;

  public clArray: CustomLayer[] = [];

  public intensity: number = 0;
  public ligthColor!: string;
  public dirX: number = 0;
  public dirY: number = 0;
  public dirZ: number = 0;

  public slidervalue: number = someValue;

  constructor(private nodeService: NodeService,
    private lineService: LineService,
    private modalService: BsModalService) { }

  /*Metodos relativos ao modo 2D */
  public modo2D(): void {
    /* previous in 3d mode */
    if (this.isLoaded3D == true) {
      //we need to remove somes layers (3d buildings + every 3d model drawed)
      this.removeLayer('3d-buildings');
      this.remove2DLayers();
      this.remove3DLayersModels();
    }
    this.formatNodes(); //Format array of nodes to { name, lat, lng }    
    this.loadLines();//Load and print lines
    this.printNodes2D('#ffa500');//Print nodes in 2D
    this.loadPopups();//LoadPopups
    this.mapSettings2D();
    this.isLoaded2D = true;
    this.isLoaded3D = false;
  }

  private printNodes2D(color: string): void {
    for (let x = 0; x < this.nodeArray.length; x++) {
      map.addSource(this.nodeArray[x].name, {
        'type': 'geojson',
        'data': {
          'type': 'Feature',
          'properties': {},
          'geometry': {
            'type': 'Point',
            'coordinates': [
              this.nodeArray[x].lng, this.nodeArray[x].lat
            ]
          }
        }
      });
      map.addLayer({
        'id': this.nodeArray[x].name,
        'type': 'circle',
        'source': this.nodeArray[x].name,
        'paint': {
          'circle-radius': 8,
          'circle-color': color
        },
        'filter': ['==', '$type', 'Point']
      });
    }
  }

  private mapSettings2D(): void {
    map.dragRotate.disable();
    map.touchZoomRotate.disableRotation();
    map.setZoom(12);
    map.setPitch(0);
    map.setCenter([-8.33448520831829, 41.2082119860192]);
  }

  private loadPopups(): void {
    let popup = new Mapboxgl.Popup({ closeButton: false, maxWidth: '500px' });
    for (let x = 0; x < this.nodeArray.length; x++) {
      map.on('mousemove', this.nodeArray[x].name, (e) => {
        map.getCanvas().style.cursor = 'pointer';

        popup.setLngLat(e.lngLat)
          .setHTML("<h4><strong>Nome:</strong>" + this.nodeArray[x].name + "</h4>" +
            "<h4><strong>Latitude:</strong>" + this.nodeArray[x].lat + "</h4>" +
            "<h4><strong>Longitude:</strong>" + this.nodeArray[x].lng + "</h4>")
          .addTo(map);
      })
      map.on('mouseleave', this.nodeArray[x].name, () => {
        map.getCanvas().style.cursor = '';
        popup.remove();
      })
    }

    for (let j = 0; j < this.pathLayers.length; j++) {
      let x = this.pathLayers[j].name + ',' + this.pathLayers[j].pathKey + ',' + this.pathLayers[j].ite;

      map.on('mousemove', x, (e) => {
        map.getCanvas().style.cursor = 'pointer';

        popup.setLngLat(e.lngLat)
          .setHTML("<h4><strong>Nome:</strong>" + this.pathLayers[j].name + "</h4>" +
            "<h4><strong>Percurso:</strong>" + this.pathLayers[j].pathKey + "</h4>")
          .addTo(map);
      })

      map.on('mouseleave', x, () => {
        map.getCanvas().style.cursor = '';
        popup.remove();
      })

    }
  }
  /*=============================*/



  /* Method that load/draw lines based upon pathNodes coordinates && removeLayer methods*/
  private loadLines(): void {
    var line = new Array();
    var coor;
    var goPath;
    var goCoord = new Array();

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
        var d = 0.00005;
        goCoord = new Array();
        goCoord.push((lngi + n * d * cosBeta).toString());
        goCoord.push((lati + n * d * sinBeta).toString());
        line.push(goCoord);
        goCoord = new Array();
        goCoord.push((lngj + n * d * cosBeta).toString());
        goCoord.push((latj + n * d * sinBeta).toString());
        line.push(goCoord);

        let idL = coor.id + ',' + goPath.key + "," + b;

        let layerJSON = {
          name: coor.id,
          pathKey: goPath.key,
          ite: b
        }

        this.pathLayers.push(layerJSON);

        console.log('idL:');

        map.addSource(idL, {
          'type': 'geojson',
          'data': {
            'type': 'Feature',
            'properties': {},
            'geometry': {
              'type': 'LineString',
              'coordinates': line
            }
          }
        });

        map.addLayer({
          'id': idL,
          'type': 'line',
          'source': idL,
          'layout': {
            'line-join': 'round',
            'line-cap': 'round'
          },
          'paint': {
            'line-color': this.rgbToHex(coor.color),
            'line-width': 4
          }
        });

        line = new Array();

        if (n <= 0) {
          this.nodeMatrix[i][j] = 1 - n;
        } else {
          this.nodeMatrix[i][j] = -n;
        }
      }
    }
  }

  private remove2DLayers(): void {
    //remove nodes
    for (let x = 0; x < this.nodeArray.length; x++) {
      if (map.getLayer(this.nodeArray[x].name)) {
        map.removeLayer(this.nodeArray[x].name);
        map.removeSource(this.nodeArray[x].name);
      }
    }
    //remove paths Paredes_Aguiar_3_0
    var coor;
    var goPath;
    for (var a = 0; a < this.coords.length; a++) {
      coor = this.coords[a];
      for (var b = 0; b < coor.goPaths.length; b++) {
        goPath = coor.goPaths[b];

        if (map.getLayer(coor.id + ',' + goPath.key + ',' + b)) {
          map.removeLayer(coor.id + ',' + goPath.key + ',' + b);
          map.removeSource(coor.id + ',' + goPath.key + ',' + b);
        }
      }
    }

  }

  private remove3DLayersModels(): void {
    for (let x = 0; x < this.nodeArray.length; x++) {
      if (map.getLayer(this.nodeArray[x].name + '3D')) {
        map.removeLayer(this.nodeArray[x].name + '3D');
      }
    }
  }

  private removeLayer(layer: string): void {
    if (map.getLayer(layer)) {
      map.removeLayer(layer);
    }
  }
  /*=============================*/



  /*Metodos relativos ao modo 3D */
  public modo3D(): void {
    this.modalRef.hide();
    if (this.isLoaded2D) {
      this.remove2DLayers();
    }
    this.mapSettings3D();
    this.formatNodes();
    this.loadLines();
    this.printNodes2D('#ff0000');
    this.loadPopups();
    this.loadDefault3DBuildings();
    this.load3DModels();
    //load bus with movement specifications
    this.isLoaded3D = true;
    this.isLoaded2D = false;
  }
  /* Load default 3d building on map */
  private loadDefault3DBuildings(): void {
    map.addLayer({
      id: '3d-buildings',
      source: 'composite',
      'source-layer': 'building',
      filter: ['==', 'extrude', 'true'],
      type: 'fill-extrusion',
      minzoom: 15,
      paint: {
        'fill-extrusion-color': '#c0b6ab',
        'fill-extrusion-height': [
          'interpolate', ['linear'], ['zoom'],
          15, 0,
          15.05, ['get', 'height']
        ],
        'fill-extrusion-base': [
          'interpolate', ['linear'], ['zoom'],
          15, 0,
          15.05, ['get', 'min_height']
        ]
      }
    });
  }
  /* Method to load 3D models (stopbus and stations) */
  private load3DModels(): void {
    for (let i = 0; i < this.nodeArray.length; i++) {
      for (let j = 0; j < listaIsDepot.length; j++) {
        if ((listaIsDepot[j].lat == this.nodeArray[i].lat) && (listaIsDepot[j].lng == this.nodeArray[i].lng)) {
          map.addLayer(new CustomLayer(this.nodeArray[i].name, this.nodeArray[i].lng, this.nodeArray[i].lat, './assets/model/estacao.gltf', this.intensity, this.ligthColor, this.dirX, this.dirY, this.dirZ));
          i++;
        }
      }
      map.addLayer(new CustomLayer(this.nodeArray[i].name, this.nodeArray[i].lng, this.nodeArray[i].lat, './assets/model/stopbus.glb', this.intensity, this.ligthColor, this.dirX, this.dirY, this.dirZ));
    }
  }

  private mapSettings3D(): void {
    map.dragRotate.enable();
    map.touchZoomRotate.enableRotation();
    map.setPitch(80);
    map.setZoom(16);
    map.setCenter([-8.33448520831829, 41.2082119860192]);
  }

  public toggleThirdPersonControls(): void {
    map.addLayer(new BusLayer('Bus', busLong, busLat, './assets/model/car2.glb'));
    map.dragRotate.enable();
    map.touchZoomRotate.enableRotation();
    map.setPitch(75);
    map.setZoom(22);
    map.easeTo({
      center: [
        busLong,
        busLat
      ],
      essential: true // this animation is considered essential with respect to prefers-reduced-motion
    });
    map.getCanvas().focus();
    map.getCanvas().addEventListener(
      'keydown',
      function (e) {
        var x = busLat + 0.00005;
        var y = busLong;
        var modelAltitude = 0;
        var modelRotate = [Math.PI / 2, 0, 0];
        var modelAsMercatorCoordinate = Mapboxgl.MercatorCoordinate.fromLngLat(
          [busLong, busLat],
          modelAltitude
        );

        var modelTransform = {
          translateX: modelAsMercatorCoordinate.x,
          translateY: modelAsMercatorCoordinate.y,
          translateZ: modelAsMercatorCoordinate.z,
          rotateX: modelRotate[0],
          rotateY: modelRotate[1],
          rotateZ: modelRotate[2],

          scale: modelAsMercatorCoordinate.meterInMercatorCoordinateUnits()
        };

        var deltaDistance = modelTransform.translateX * 50;
        var deltaDegrees = 10;
        function easing(t: number) {
          return t * (2 - t);
        };


        e.preventDefault();
        switch (e.key) {
          case 'W':
          case 'w':
            // up
            console.log("W" + map.getBearing());
            console.log("Lat antes: " + busLat);
            console.log("Long antes: " + busLong);
            // map.panBy([0, -deltaDistance], {
            //   easing: easing
            // });
            var valor = (map.getBearing() * (Math.PI / 180));
            console.log("kekw " + valor);
            busLat = busLat + (x - busLat) * Math.cos(valor) -
              ((y - busLong) * Math.sin(valor));
            busLong = busLong + (x - busLat) * Math.sin(valor) +
              ((y - busLong) * Math.cos(valor));
            console.log("Lat depois: " + busLat);
            console.log("Long depois: " + busLong);
            map.jumpTo({
              center: [
                busLong,
                busLat
              ]
            });
            break;
          case 'S':
          case 's':
            // down
            console.log("S" + map.getBearing());

            // map.panBy([0, deltaDistance], {
            //   easing: easing
            // });
            /*var valor = (map.getBearing() * (Math.PI / 180)) + Math.PI;
            busLat = busLat + (x - busLat) * Math.cos(valor) -
              ((y - busLong) * Math.sin(valor));
            busLong = busLong + (x - busLat) * Math.sin(valor) +
              ((y - busLong) * Math.cos(valor));*/
            busLat -= 0.00005;
            map.jumpTo({
              center: [
                busLong,
                busLat
              ]
            });
            break;
          case 'A':
          case 'a':
            // left
            console.log("A" + map.getBearing());
            var valor = (map.getBearing() * (Math.PI / 180));

            map.easeTo({
              bearing: map.getBearing() - deltaDegrees,
              easing: easing
            });
            rotateBus += deltaDegrees * (Math.PI / 180);

            break;
          case 'D':
          case 'd':
            // right
            console.log("D" + map.getBearing());

            map.easeTo({
              bearing: map.getBearing() + deltaDegrees,
              easing: easing
            });
            rotateBus -= deltaDegrees * (Math.PI / 180);

            break;
        }
      }
    )

  }


  /*=============================*/

  public lights(): void {
    someValue = this.slidervalue / 100;
    changedLight = true;
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template, { class: 'modal-sm' });
  }

  /*----Method to get data (nodes and lines/paths) to be able to draw in the map later----*/
  /* Method that subscribes the nodes from web api */
  private getNodes(order: any): void {
    this.nodeService.getNodes(order)
      .subscribe(nodes => {
        this.nodes = nodes;
        //se for relief point adiciona-se também a uma lista de reliefPoints;
        nodes.forEach(eachNode => {
          if (eachNode.isDepot) {
            const latLng = {
              lat: eachNode.latitude.valueOf(),
              lng: eachNode.longitude.valueOf()
            }

            listaIsDepot.push(latLng);
          }
        });
        console.log('Nós Carregados...');
      });
  }
  /* Picks the subscribed nodes and formated the data to return a new array */
  private formatNodes(): any[] {
    var ar = new Array();
    for (var i = 0; i < this.nodes.length; i++) {
      var json = {
        name: this.nodes[i].name,
        lat: this.nodes[i].latitude.valueOf(),
        lng: this.nodes[i].longitude.valueOf()
      }
      ar.push(json);
      this.nodeMatrix[i] = [0];
      this.nodeArray[i] = json;
      for (var j = 0; j < this.nodes.length; j++) {
        this.nodeMatrix[i][j] = 0;
      }
    }
    console.log('Nós formatados para {name,lat,lng}...');
    return ar;
  }
  /* Method that get the lines from web api and stores the coordinates in a Json array object */
  private getCoords(order: any): void {
    this.lineService.getLinesID(order).subscribe(lines => {
      this.lines = lines;

      //console.log('getLines....');
      for (var i = 0; i < this.lines.length; i++) {
        //console.log('line :   ' + this.lines[i].lineId);
        this.lineService.getCoordsFromLine(this.lines[i].lineId).subscribe(coord => {
          //console.log(JSON.stringify(coord));
          this.coords.push(coord);
        })
      }
    })
  }
  /* Method that returns the index from nodeCoords */
  private getIndexFromCoordenates(nodeCoords: any): number {
    var index = this.nodeArray.findIndex(function (node, i) {
      return node.lat === nodeCoords.lat && node.lng === nodeCoords.lng;
    });
    return index.valueOf();
  }



  /*----Methods to convert rbg color to hex and vice-versa----*/
  private rgbToHex(input: string): string {
    var rgb = input.split(',');
    var r = parseInt(rgb[0].substring(4));
    var g = parseInt(rgb[1]);
    var b = parseInt(rgb[2]);
    return this.rgb_to_hex(r, g, b);
  }
  /* Source : https://stackoverflow.com/questions/5623838/rgb-to-hex-and-hex-to-rgb */
  private rgb_to_hex(red: number, green: number, blue: number) {
    const rgb = (red << 16) | (green << 8) | (blue << 0);
    return '#' + (0x1000000 + rgb).toString(16).slice(1);
  }



  /* Create map with mapbox api */
  private createMap(): void {
    map = new Mapboxgl.Map({
      accessToken: environment.mapboxKey,
      container: 'mapa-mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [-8.33448520831829, 41.2082119860192],
      antialias: true,
      zoom: 12
    });
  }

  ngOnInit() {
    this.getNodes('name');
    this.getCoords('name');
    this.createMap();
  }
}



/* Class responsable to create a custom 3d model layer */
class CustomLayer {
  id: string;
  type: any;
  camera: THREE.Camera;
  scene: THREE.Scene;
  renderer !: THREE.WebGLRenderer;
  lng !: number;
  lat !: number;
  path !: string;
  mixer !: any;
  clock !: THREE.Clock;

  constructor(layerid: string, lng: number, lat: number, path: string, intensity: number, lightColor: string, dirX: number, dirY: number, dirZ: number) {

    this.lng = lng;
    this.lat = lat;
    this.path = path;

    this.id = layerid + '3D';
    this.type = 'custom';

    this.camera = new THREE.Camera();
    this.scene = new THREE.Scene();

    //lights

    console.log('int:' + intensity + 'lightColor:' + lightColor + 'x' + dirX + dirY + dirZ);


    var a = new THREE.AmbientLight(0xffffff, 0.2);
    this.scene.add(a);

    var directionalLight = new THREE.DirectionalLight(lightColor, intensity);
    directionalLight.position.set(dirX, dirY, dirZ);
    this.scene.add(directionalLight);

    var directionalLight = new THREE.DirectionalLight(lightColor, intensity);
    directionalLight.position.set(dirX, -dirY, dirZ);
    this.scene.add(directionalLight);

    //loader
    var loader = new GLTFLoader();
    loader.load(this.path, ((object: any) => {
      this.scene.traverse(c => {
        c.castShadow = true;
      });
      this.scene.add(object.scene);
    }).bind(this));
  }

  onAdd(m: any, gl: any) {

    map = m;
    this.renderer = new THREE.WebGLRenderer({
      canvas: map.getCanvas(),
      context: gl
    });

    this.renderer.autoClear = false;
  }

  render(gl: any, matrix: any) {

    const rotationX = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(1, 0, 0), this.setRelativeCoordinatesToModel().rotateX);
    const rotationY = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(0, 1, 0), this.setRelativeCoordinatesToModel().rotateY);
    const rotationZ = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(0, 0, 1), this.setRelativeCoordinatesToModel().rotateZ);

    const m = new THREE.Matrix4().fromArray(matrix);
    const l = new THREE.Matrix4().makeTranslation(this.setRelativeCoordinatesToModel().translateX, this.setRelativeCoordinatesToModel().translateY, this.setRelativeCoordinatesToModel().translateZ)
      .scale(new THREE.Vector3(this.setRelativeCoordinatesToModel().scale / 80, -this.setRelativeCoordinatesToModel().scale / 80, this.setRelativeCoordinatesToModel().scale / 80))
      .multiply(rotationX)
      .multiply(rotationY)
      .multiply(rotationZ);

    this.camera.projectionMatrix.elements = matrix;
    this.camera.projectionMatrix = m.multiply(l);
    this.renderer.state.reset();
    this.renderer.render(this.scene, this.camera);
    map.triggerRepaint();
  }

  public setRelativeCoordinatesToModel(): any {

    var modelAltitude = 0;
    var modelRotate = [Math.PI / 2, 0, 0];
    var modelAsMercatorCoordinate = Mapboxgl.MercatorCoordinate.fromLngLat(
      [this.lng, this.lat],
      modelAltitude
    );

    var modelTransform = {
      translateX: modelAsMercatorCoordinate.x,
      translateY: modelAsMercatorCoordinate.y,
      translateZ: modelAsMercatorCoordinate.z,
      rotateX: modelRotate[0],
      rotateY: modelRotate[1],
      rotateZ: modelRotate[2],

      scale: modelAsMercatorCoordinate.meterInMercatorCoordinateUnits()
    };
    return modelTransform;
  }
}

class BusLayer {
  id: string;
  type: any;
  camera: THREE.Camera;
  scene: THREE.Scene;
  renderer !: THREE.WebGLRenderer;
  lng !: number;
  lat !: number;
  path !: string;

  constructor(layerid: string, lng: number, lat: number, path: string) {

    this.lng = lng;
    this.lat = lat;
    this.path = path;
    this.id = layerid + '3D';

    this.id = 'custom_layer' + lat;
    this.type = 'custom';

    this.camera = new THREE.Camera();
    this.scene = new THREE.Scene();

    //lights
    const directionalLight = new THREE.DirectionalLight(0xffffff);
    directionalLight.position.set(0, -70, 100).normalize();
    this.scene.add(directionalLight);

    const directionalLight2 = new THREE.DirectionalLight(0xffffff);
    directionalLight2.position.set(0, 70, 100).normalize();
    this.scene.add(directionalLight2);

    //loader
    var loader = new GLTFLoader();
    loader.load(this.path, ((object: any) => {
      this.scene.add(object.scene);
    }).bind(this));

  }



  onAdd(m: any, gl: any) {
    map = m;

    this.renderer = new THREE.WebGLRenderer({
      canvas: map.getCanvas(),
      context: gl
    });

    this.renderer.autoClear = false;
  }

  render(gl: any, matrix: any) {
    //console.log(busLong, busLat);

    const rotationX = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(1, 0, 0), this.setRelativeCoordinatesToModel().rotateX);
    const rotationY = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(0, 1, 0), this.setRelativeCoordinatesToModel().rotateY);
    const rotationZ = new THREE.Matrix4().makeRotationAxis(new THREE.Vector3(0, 0, 1), this.setRelativeCoordinatesToModel().rotateZ);

    const m = new THREE.Matrix4().fromArray(matrix);
    const l = new THREE.Matrix4().makeTranslation(this.setRelativeCoordinatesToModel().translateX, this.setRelativeCoordinatesToModel().translateY, this.setRelativeCoordinatesToModel().translateZ)
      .scale(new THREE.Vector3(this.setRelativeCoordinatesToModel().scale / 10, -this.setRelativeCoordinatesToModel().scale / 10, this.setRelativeCoordinatesToModel().scale / 10))
      .multiply(rotationX)
      .multiply(rotationY)
      .multiply(rotationZ);

    this.camera.projectionMatrix.elements = matrix;
    this.camera.projectionMatrix = m.multiply(l);
    this.renderer.state.reset();
    this.renderer.render(this.scene, this.camera);
    map.triggerRepaint();
  }

  public setRelativeCoordinatesToModel(): any {

    var modelAltitude = 0;
    var modelRotate = [Math.PI / 2, rotateBus, 0];
    var modelAsMercatorCoordinate = Mapboxgl.MercatorCoordinate.fromLngLat(
      [busLong, busLat],
      modelAltitude
    );

    var modelTransform = {
      translateX: modelAsMercatorCoordinate.x,
      translateY: modelAsMercatorCoordinate.y,
      translateZ: modelAsMercatorCoordinate.z,
      rotateX: modelRotate[0],
      rotateY: modelRotate[1],
      rotateZ: modelRotate[2],

      scale: modelAsMercatorCoordinate.meterInMercatorCoordinateUnits()
    };
    return modelTransform;
  }
}
