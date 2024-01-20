import { Injectable } from '@nestjs/common';
import { TokenManager } from 'src/cryptography/abstracts/token-manager';
import { IReturnUser } from 'src/interfaces/IReturnUser';
import { IService } from 'src/interfaces/IService';
import { IUserPayload } from 'src/modules/auth/models/IUserPayload';

// Service responsável por fornecer o JWT !!!

@Injectable()
export class LoginUserService implements IService {
    constructor(private readonly _tokenManager: TokenManager) {}

    async execute(data: IReturnUser): Promise<string> {
        const payload: IUserPayload = {
            sub: data.id,
            name: data.name,
            email: data.email,
        };

        const JWT = this._tokenManager.generate(payload);

        return JWT;
    }
}
