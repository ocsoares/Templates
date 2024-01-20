import { IReturnUser } from 'src/interfaces/IReturnUser';

export class UserPresenter {
    static toJSONOutput({
        firstName,
        lastName,
        email,
        createdAt,
        updatedAt,
    }: IReturnUser): IReturnUser {
        return {
            firstName,
            lastName,
            email,
            createdAt,
            updatedAt,
        };
    }

    static toJSONOutputArray(users: IReturnUser[]): IReturnUser[] {
        return users.map(
            ({ firstName, lastName, email, createdAt, updatedAt }) =>
                <IReturnUser>{
                    firstName,
                    lastName,
                    email,
                    createdAt,
                    updatedAt,
                },
        );
    }
}
