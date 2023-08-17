import { IUser } from './IUser';

export interface IUserWithoutPassword extends Omit<IUser, 'password'> {}
