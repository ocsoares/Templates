import { Request, Response, NextFunction } from 'express';
import { ErrorAPIHelper } from '../helpers/errorAPIHelper';

export const pageNotFound = (req: Request, res: Response): Response => {
    return res.status(404).json({
        message: 'Page not found !'
    });
};

export const errorAPIMiddleware = (
    error: Error & Partial<ErrorAPIHelper>,
    req: Request,
    res: Response,
    next: NextFunction
): Response => {

    // Se tiver um Erro inesperado no Código que eu não sei qual é !! <<
    const statusCode = error.statusCode ? error.statusCode : 500;

    // Se tiver um erro inesperado no Código, ao invés de mandar uma mensa-
    // gem Padrão e Desformatada, manda um 'Internal Server Error' !! <<
    const message = error.statusCode ? error.message : 'Internal Server Error';

    console.log('Error:', error);
    return res.status(statusCode).json({
        error: error.name,
        message
    });
};