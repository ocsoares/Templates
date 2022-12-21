export interface IUser {
    username: string;
    email: string;
    password: string;
}

export interface IAnyCaseUseCaseMethods {
    execute(data: IUser): Promise<IUser>;
}

export interface IAnyCaseController {
    handle(req: Request, res: Response): Promise<Response>;
}