import { IUser } from "../useCases/AnyCase/IAnyCase";

export interface IAnyRepository {
    // Default methods to use with any Database

    findByUsername(username: string): Promise<IUser>;
    save(toSave: IUser): Promise<void>; // object = User Interface

    // More methods...
}