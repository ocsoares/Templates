import { Request, Response, NextFunction } from 'express';
import { AnyCaseUserCase } from './AnyCaseUseCase';
import { IAnyCaseController, IUser } from './IAnyCase';

export class AnyController implements IAnyCaseController {
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