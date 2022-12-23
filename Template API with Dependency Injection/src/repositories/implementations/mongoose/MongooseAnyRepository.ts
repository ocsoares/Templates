import { IUser } from "../../../useCases/anyCase (ex. createUser)/IAnyCase";
import { IAnyRepository } from "../../IAnyRepository";

// IMPORTANTE: Esse arquivo é Responsável pelos Métodos REAIS do Banco de Dados-
// (Mongoose, no caso) !!! <<<

// Nome do ARQUIVO = Banco + NOME da Interface implementada Abaixo SEM o I !!! <<<

export class MongoDBAnyRepository implements IAnyRepository {
    async findByUsername(username: string): Promise<IUser> {
        // Fazer daquele jeito do Mongoose com find blabla.... !!!!

        // Just to not return an error !!
        const getUser: IUser = {
            username: 'any',
            email: 'any',
            password: 'any'
        };

        return getUser;
    }

    async save(toSave: IUser): Promise<void> {

    }
}