import { IUseCase } from "../../@types/interfaces/IUseCase";
import { IUser } from "../../models/IUser";
import { IAnyRepository } from "../../repositories/interfaces/IAnyRepository";
import { IAnyRequest } from "./IAnyCase";

// NOME do ARQUIVO = Case (geralmente o Nome da Pasta) + UseCase !! << 

export class AnyCaseUserCase implements IUseCase {
    constructor(
        private readonly anyRepository: IAnyRepository // Repository with database methods
    ) { }

    async execute(data: IAnyRequest): Promise<IUser> {
        const userAlreadyExists = await this.anyRepository.findByUsername(data.username);

        if (userAlreadyExists) {
            // ERROR !!
            // code....
        }

        const newUser = data;

        await this.anyRepository.save(newUser);

        return newUser;
    }
}