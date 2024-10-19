export interface PathModel{
    pathId: string,
    key: string,
    pathNodes : [{
        duracao : Number,
        distancia : Number, 
        inicialNode : string,
        finalNode : string,
    }]
}