import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';
import { StatusCodes } from 'http-status-codes';

// Esse é um Middleware "coringa", que vai funcionar com OUTRO Middleware especificando os Dados e as Validações !! <<

export const handleValidation = (req: Request, res: Response, next: NextFunction) => {
    // Vai trazer os Erros da Requisição !! <<
    const errors = validationResult(req);

    // Se ele NÃO encontrar algum erro, vai Retornar vazio, então Next !! <<
    if (errors.isEmpty()) {
        return next();
    }

    // Array para Armazenar os Erros !! <<
    const getErrors: object[] = [];

    // Se houver erros, coloca eles no array getErrors !! <<
    errors.array().map((error) => getErrors.push({ [error.param]: error.msg }));

    // Retorna um status code com o Erro !! <<
    return res.status(StatusCodes.BAD_REQUEST).json({
        errors: getErrors // Retorna os Erros dentro um Array !! <<
    });
};