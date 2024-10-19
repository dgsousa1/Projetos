import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";
import { Result } from "../core/logic/Result";
import IMockController from './IControllers/IMockController';


export default class MockController implements IMockController {
    constructor(
    ) { }

    public async getTrip(req: Request, res: Response, next: NextFunction) {
        var mockTrips = new Array();
        var m1 = {
            path: '3',
            schedule: [73800, 74280, 74580, 74880, 75420]
        }
        var m2 = {
            path: '3',
            schedule: [72900, 73380, 73680, 73980, 74520]

        }
        var m3 = {
            path: '3',
            schedule: [72000, 72480, 72780, 73080, 73620]
        }
        var m4 = {
            path: '3',
            schedule: [71100, 71580, 71880, 72180, 72720]
        }
        var m5 = {
            path: '3',
            schedule: [70200, 70680, 70980, 71280, 71820]
        }
        var m6 = {
            path: '3',
            schedule: [69300, 69780, 70080, 70380, 70920]
        }
        var m7 = {
            path: '1',
            schedule: [54000, 54540, 54840, 55140, 55620]
        }
        var m8 = {
            path: '1',
            schedule: [55800, 56340, 56640, 56940, 57420]

        }
        var m9 = {
            path: '1',
            schedule: [57600, 58140, 58440, 58740, 59220]

        }
        var m10 = {
            path: '11',
            schedule: [49020, 49500, 49740, 49980, 50760]

        }
        var m11 = {
            path: '11',
            schedule: [50820, 51540, 51540, 51780, 52560]

        }
        var m12 = {
            path: '11',
            schedule: [47220, 47700, 47940, 48180, 48960]

        }
        var m13 = {
            path: '9',
            schedule: [50760, 51540, 51780, 52020, 52500]

        }
        var m14 = {
            path: '9',
            schedule: [52560, 53340, 53580, 53820, 54300]

        }
        var m15 = {
            path: '9',
            schedule: [54360, 55140, 55380, 55620, 56100]

        }
        var m16 = {
            path: '9',
            schedule: [47220, 47700, 47940, 48180, 48960]

        }
        var m17 = {
            path: '38',
            schedule: [30000, 30120]

        }
        var m18 = {
            path: '38',
            schedule: [67800, 67800]

        }
        var m19 = {
            path: '38',
            schedule: [27300, 27420]

        }
        var m20 = {
            path: '35',
            schedule: [75840, 75960]

        }
        var m21 = {
            path: '35',
            schedule: [76320, 76440]

        }
        var m22 = {
            path: '24',
            schedule: [49140, 49440, 49680, 49920, 50160, 50460]

        }
        var m23 = {
            path: '24',
            schedule: [43860, 44160, 44400, 44640, 44880, 45180]

        }
        var m24 = {
            path: '20',
            schedule: [50760, 51060, 51300, 51540, 51780, 52028]

        }
        var m25 = {
            path: '20',
            schedule: [57360, 57660, 57900, 58140, 58380, 58680]

        }
        var m25 = {
            path: '37',
            schedule: [77160, 77460]

        }
        mockTrips.push(m1);
        mockTrips.push(m2);
        mockTrips.push(m3);
        mockTrips.push(m4);
        mockTrips.push(m5);
        mockTrips.push(m6);
        mockTrips.push(m7);
        mockTrips.push(m8);
        mockTrips.push(m9);
        mockTrips.push(m10);
        mockTrips.push(m11);
        mockTrips.push(m12);
        mockTrips.push(m13);
        mockTrips.push(m14);
        mockTrips.push(m15);
        mockTrips.push(m16);
        mockTrips.push(m17);
        mockTrips.push(m18);
        mockTrips.push(m19);
        mockTrips.push(m20);
        mockTrips.push(m21);
        mockTrips.push(m22);
        mockTrips.push(m23);
        mockTrips.push(m24);
        mockTrips.push(m25);

        return res.status(201).json(mockTrips);

    }
};