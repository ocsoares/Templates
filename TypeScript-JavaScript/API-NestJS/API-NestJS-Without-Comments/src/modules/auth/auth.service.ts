import { Injectable } from '@nestjs/common';
import { UserRepository } from '../../repositories/abstracts/UserRepository';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';
import { InvalidCredentialsException } from 'src/exceptions/auth-exceptions/invalid-credentials.exception';
import { PasswordHasher } from 'src/cryptography/abstracts/password-hasher';

interface IAuthService {
    validateUser(
        email: string,
        password: string,
    ): Promise<IUserWithoutPassword>;
}

@Injectable()
export class AuthService implements IAuthService {
    constructor(
        private readonly userRepository: UserRepository,
        private readonly passwordHasher: PasswordHasher,
    ) {}

    async validateUser(
        email: string,
        password: string,
    ): Promise<IUserWithoutPassword> {
        const user = await this.userRepository.findByEmail(email);

        if (!user) {
            throw new InvalidCredentialsException();
        }

        const isValidPassword = await this.passwordHasher.compare(
            password,
            user.password,
        );

        if (!isValidPassword) {
            throw new InvalidCredentialsException();
        }

        // eslint-disable-next-line prettier/prettier, @typescript-eslint/naming-convention, @typescript-eslint/no-unused-vars
        const { password:_, ...returnUser } = user

        return returnUser as IUserWithoutPassword;
    }
}
