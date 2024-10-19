export interface TripLineModel {
    name: string,
    goPaths: [{
        key: number,
        pathNodes: [{
            id: string,
            duracao: number,
            distancia: number,
            inicialNode: string,
            finalNode: string
        }]
    }],
    returnPaths: [{
        key: number,
        pathNodes: [{
            id: string,
            duracao: number,
            distancia: number,
            inicialNode: string,
            finalNode: string
        }]
    }],
}
