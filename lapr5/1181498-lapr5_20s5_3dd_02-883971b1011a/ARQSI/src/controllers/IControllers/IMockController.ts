import { Request, Response, NextFunction } from 'express';

export default interface IMockController  {
  getTrip(req: Request, res: Response, next: NextFunction);
}