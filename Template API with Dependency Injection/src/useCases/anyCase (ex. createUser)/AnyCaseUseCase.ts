import { IUseCase } from "../../@types/interfaces/IUseCase";
import { IAnyRepository } from "../../repositories/IAnyRepository";
import { IUser } from "./IAnyCase";

// NOME do ARQUIVO = Case (geralmente o Nome da Pasta) + UseCase !! << 

export class AnyCaseUserCase implements IUseCase {
    constructor(
        private readonly anyRepository: IAnyRepository // Repository with database methods
    ) { }

    async execute(data: IUser): Promise<IUser> {
        const userAlreadyExists = await this.anyRepository.findByUsername(data.username);

        if (userAlreadyExists) {
            // code...
        }

        // Database Model instead of IUser !!
        // VER se é pra usar o Model ou a Interface, pq o Model varia do Banco !!!!!
        const newUser: IUser = {
            username: 'any',
            email: 'any',
            password: 'any'
        };

        await this.anyRepository.save(newUser);

        return newUser;
    }
}