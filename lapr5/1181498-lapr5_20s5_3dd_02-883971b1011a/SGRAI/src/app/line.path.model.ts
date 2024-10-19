export interface LinePathModel {
    id: string,
    goPaths: [{
        pathId: string,
        key: Number,
        pathNodes: [{
            duracao: Number,
            distancia: Number,
            inicialNode: string,
            finalNode: string
        }]
    }]
    returnPaths: [{
        pathId: string;
        key: Number,
        pathNodes: [{
            duracao: Number,
            distancia: Number,
            inicialNode: string,
            finalNode: string,
        }]

    }]
}