import { IUser } from "../../models/IUser";

// POR EXEMPLO, o Nome desse Arquivo e da Interface poderia ser IUsersRepository, por causa dos seus Métodos !!!! <<<
// Ou também poderia chamar ICreateUserRepository !!! <<<

export interface IAnyRepository {
    // Default methods to use with any Database

    findByUsername(username: string): Promise<IUser>;
    save(toSave: IUser): Promise<void>;

    // More methods...
}