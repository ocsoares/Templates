import { IUser } from "../useCases/anyCase (ex. createUser)/IAnyCase";

// POR EXEMPLO, o Nome desse Arquivo e da Interface poderia ser IUsersRepository, por causa dos seus Métodos !!!! <<<

export interface IAnyRepository {
    // Default methods to use with any Database

    findByUsername(username: string): Promise<IUser>;
    save(toSave: IUser): Promise<void>; // object = User Interface

    // More methods...
}