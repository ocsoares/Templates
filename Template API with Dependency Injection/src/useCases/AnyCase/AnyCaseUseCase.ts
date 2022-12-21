import { IAnyRepository } from "../../repositories/IAnyRepository";
import { IAnyCaseUseCaseMethods, IUser } from "./IAnyCase";

export class AnyCaseUserCase implements IAnyCaseUseCaseMethods {
    constructor(
        private readonly anyRepository: IAnyRepository // Repository with database methods
    ) { }

    async execute(data: IUser): Promise<IUser> {
        const userAlreadyExists = await this.anyRepository.findByUsername(data.username);

        if (userAlreadyExists) {
            // code...
        }

        // Database Model instead of IUser !!
        const newUser: IUser = {
            username: 'any',
            email: 'any',
            password: 'any'
        };

        await this.anyRepository.save(newUser);

        return newUser;
    }
}