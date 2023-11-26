import { Global, Module } from '@nestjs/common';
import { TokenManager } from 'src/cryptography/abstracts/token-manager';
import { JwtManager } from './jwt-manager';
import { JwtModule } from '@nestjs/jwt';

@Global()
@Module({
    imports: [
        JwtModule.registerAsync({
            useFactory: async () => ({
                signOptions: {
                    algorithm: 'RS256',
                    expiresIn: process.env.JWT_EXPIRATION,
                },
                privateKey: Buffer.from(process.env.JWT_PRIVATE_KEY, 'base64'),
                publicKey: Buffer.from(process.env.JWT_PUBLIC_KEY, 'base64'),
            }),
        }),
    ],
    providers: [
        {
            provide: TokenManager,
            useClass: JwtManager,
        },
    ],
    exports: [TokenManager],
})
export class JwtManagerModule {}
