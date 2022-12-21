import { IUser } from "../../useCases/AnyCase/IAnyCase";
import { IAnyRepository } from "../IAnyRepository";

export class MongoDBAnyRepository implements IAnyRepository {
    async findByUsername(username: string): Promise<IUser> {

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