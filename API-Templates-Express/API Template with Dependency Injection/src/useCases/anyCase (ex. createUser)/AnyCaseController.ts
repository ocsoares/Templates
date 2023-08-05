import { Request, Response } from 'express';
import { IController } from '../../@types/interfaces/IController';
import { IUser } from '../../models/IUser';
import { AnyCaseUserCase } from './AnyCaseUseCase';

// NOME do Arquivo = Case (geralmente o Nome da Pasta) + Controller !! <<

export class AnyController implements IController {
    constructor(
        private readonly anyCaseUserCase: AnyCaseUserCase // Case responsible, in this case, for CREATE a new user !!
    ) { }

    async handle(req: Request, res: Response): Promise<Response> {
        const { username, email, password }: IUser = req.body;

        const createdAccount = await this.anyCaseUserCase.execute({
            username,
            email,
            password
        });

        return res.status(201).json({
            message: 'any message',
            createdAccount
        });
    }
};