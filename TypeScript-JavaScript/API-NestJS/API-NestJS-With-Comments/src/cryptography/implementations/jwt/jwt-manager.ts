import { TokenManager } from 'src/cryptography/abstracts/token-manager';
import { JwtService } from '@nestjs/jwt';
import { Injectable } from '@nestjs/common';

@Injectable()
export class JwtManager implements TokenManager {
    constructor(private readonly jwtService: JwtService) {}

    async generate(payload: object, expiresIn?: string): Promise<string> {
        if (expiresIn) {
            return this.jwtService.signAsync(payload, { expiresIn });
        }

        return this.jwtService.signAsync(payload);
    }
    async verify(token: string): Promise<object> {
        return this.jwtService.verifyAsync(token);
    }
}
