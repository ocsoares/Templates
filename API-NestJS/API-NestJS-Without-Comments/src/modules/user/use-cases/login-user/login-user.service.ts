import { Injectable } from '@nestjs/common';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';
import { IService } from 'src/interfaces/IService';
import { IUserPayload } from 'src/modules/auth/models/IUserPayload';
import { TokenManager } from 'src/cryptography/abstracts/token-manager';

@Injectable()
export class LoginUserService implements IService {
    constructor(private readonly tokenManager: TokenManager) {}

    async execute(data: IUserWithoutPassword): Promise<string> {
        const payload: IUserPayload = {
            sub: data.id,
            name: data.name,
            email: data.email,
        };

        const JWT = this.tokenManager.generate(payload);

        return JWT;
    }
}
