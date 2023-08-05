import { IUser } from 'src/models/IUser';

export abstract class UserRepository {
    abstract create(data: IUser): Promise<IUser>;
    abstract findById(id: string): Promise<IUser>;
    abstract findByName(name: string): Promise<IUser>;
    abstract findByEmail(email: string): Promise<IUser>;
}
