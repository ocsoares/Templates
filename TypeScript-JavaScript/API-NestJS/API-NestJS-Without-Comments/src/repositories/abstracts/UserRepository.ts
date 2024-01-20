import { IUser } from 'src/models/IUser';
import { CreateUserDTO } from 'src/modules/user/use-cases/create-user/dtos/CreateUserDTO';
import { UpdateUserDTO } from 'src/modules/user/use-cases/update-user/dtos/UpdateUserDTO';

export abstract class UserRepository {
    abstract create(data: CreateUserDTO): Promise<IUser>;
    abstract findById(id: string): Promise<IUser>;
    abstract findByName(name: string): Promise<IUser>;
    abstract findByEmail(email: string): Promise<IUser>;
    abstract findAllUsers(): Promise<IUser[]>;
    abstract deleteOneById(id: string): Promise<IUser>;
    abstract updateOneById(id: string, data: UpdateUserDTO): Promise<IUser>;
}
