export interface PathModel{
    pathId: string,
    key: string,
    pathNodes : [{
        duracao : number,
        distancia : number, 
        inicialNode : string,
        finalNode : string,
    }]
}