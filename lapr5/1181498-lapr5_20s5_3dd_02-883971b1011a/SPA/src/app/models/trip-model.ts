export interface TripModel{
    tripNumber: number,
    orientation: string,
    line: string,
    path: number,
    isGenerated: boolean,
    passingTimes : [{
        number : number,
        time : number, 
        nodeName : string,
        isUsed : boolean,
        isReliefPoint : boolean,
    }]
}

