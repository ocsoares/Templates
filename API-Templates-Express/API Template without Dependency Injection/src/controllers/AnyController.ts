import { Request, Response, NextFunction } from 'express';

export class AnyController {
    static async anyAny(req: Request, res: Response, next: NextFunction): Promise<Response> {
        // Code...
    }
};