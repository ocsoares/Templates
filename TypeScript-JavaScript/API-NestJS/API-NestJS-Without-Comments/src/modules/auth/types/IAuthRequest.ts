import { Request } from 'express';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';

export interface IAuthRequest extends Request {
    user: IUserWithoutPassword;
}
