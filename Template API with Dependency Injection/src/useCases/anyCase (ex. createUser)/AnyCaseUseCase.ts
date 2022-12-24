import { IUseCase } from "../../@types/interfaces/IUseCase";
import { IUser } from "../../models/IUser";
import { IAnyRepository } from "../../repositories/interfaces/IAnyRepository";

// NOME do ARQUIVO = Case (geralmente o Nome da Pasta) + UseCase !! << 

export class AnyCaseUserCase implements IUseCase {
    constructor(
        private readonly anyRepository: IAnyRepository // Repository with database methods
    ) { }

    async execute(data: IUser): Promise<IUser> {
        const userAlreadyExists = await this.anyRepository.findByUsername(data.username);

        if (userAlreadyExists) {
            // ERROR !!
            // code....
        }

        const newUser: IUser = data;

        await this.anyRepository.save(newUser);

        return newUser;
    }
}