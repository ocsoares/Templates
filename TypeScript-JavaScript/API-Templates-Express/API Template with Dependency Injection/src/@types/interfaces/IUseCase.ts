import { IUser } from "../../useCases/anyCase (ex. createUser)/IAnyCase";

export interface IUseCase {
    execute(data: IUser): Promise<IUser>;
}