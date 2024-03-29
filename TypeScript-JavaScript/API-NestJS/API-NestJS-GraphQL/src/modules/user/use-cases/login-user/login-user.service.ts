import { Injectable } from '@nestjs/common';
import { IService } from 'src/interfaces/IService';
import { UserRepository } from '../../../../repositories/abstracts/UserRepository';
import { LoginUserDTO } from './dtos/LoginUserDTO';
import { TokenType } from 'src/graphql/types/token.type';
import { InvalidCredentialsException } from '../../../../exceptions/auth-exceptions/invalid-credentials.exception';
import { ITokenPayload } from 'src/interfaces/ITokenPayload';
import { PasswordHasher } from 'src/cryptography/abstracts/password-hasher';
import { TokenManager } from 'src/cryptography/abstracts/token-manager';

@Injectable()
export class LoginUserService implements IService {
    constructor(
        private readonly userRepository: UserRepository,
        private readonly passwordHasher: PasswordHasher,
        private readonly tokenManager: TokenManager,
    ) {}

    async execute(data: LoginUserDTO): Promise<TokenType> {
        const user = await this.userRepository.findByEmail(data.email);

        if (!user) {
            throw new InvalidCredentialsException();
        }
        const isValidPassword = await this.passwordHasher.compare(
            data.password,
            user.password,
        );

        if (!isValidPassword) {
            throw new InvalidCredentialsException();
        }

        const token = await this.generateToken({
            sub: user.id,
            name: user.name,
            email: user.email,
        });

        return {
            token,
        };
    }

    private async generateToken(payload: ITokenPayload): Promise<string> {
        const generatedToken = await this.tokenManager.generate(payload);

        return generatedToken;
    }
}
