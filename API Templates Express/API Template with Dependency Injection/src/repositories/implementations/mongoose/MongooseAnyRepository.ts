import { IUser } from "../../../models/IUser";
import { IAnyRepository } from "../../interfaces/IAnyRepository";

// IMPORTANTE: Esse arquivo é Responsável pelos Métodos REAIS do Banco de Dados-
// (Mongoose, no caso) !!! <<<

// Nome do ARQUIVO = Banco + NOME da Interface implementada Abaixo SEM o I !!! <<<
// OBS: Esse Arquivo poderia chamar MongooseUsersRepository(melhor) ou MongooseCreaterUserRepository !!! <<<

// IMPORTANTE, se, por exemplo, esse Repositório for sobre Usuários, Fazer TODOS os Métodos rela-
// -cionado, por exemplo, create, delete, find um usuário, etc... !!! <<< 

// IMPORTANTE: Quando for CRIAR um NOVO Usuário, depois de criado, PROCURAR pelo ID para ver se 
// criou mesmo, destruturando o id e checar com if e error !!!
export class MongooseAnyRepository implements IAnyRepository {
    private readonly _UserMongooseModel = mongoose.model('any', new Schema<IUser>({
        username: { type: String, required: true, unique: true },
        email: { type: String, required: true, unique: true },
        password: { type: String, required: true }
    }));

    async findByUsername(username: string): Promise<IUser> {

        const userAlreadyExists = await this._UserMongooseModel.find({ username });

        return userAlreadyExists;
    }

    async save(dataToSave: IUser): Promise<void> {
        const toSave = new this._UserMongooseModel(dataToSave);

        await toSave.save();
    }
}