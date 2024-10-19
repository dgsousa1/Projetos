export interface PathCoordsModel {
    id: string,
    color: string,
    goPaths: [{
        key: string
        lan1: string,
        lng1: string,
        lan2: string,
        lng2: string,
    }]
    returnPaths: [{
        key: string
        lan1: string,
        lng1: string,
        lan2: string,
        lng2: string,
    }]
}