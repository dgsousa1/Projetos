import { Request, Response, NextFunction } from 'express';

export default interface IImportFileController  {
    importFile(req: Request, res: Response, next: NextFunction);
}