import { Global, Module } from '@nestjs/common';
import { PasswordHasher } from 'src/cryptography/abstracts/password-hasher';
import { BcryptHasher } from './bcrypt-hasher';

@Global()
@Module({
    providers: [
        {
            provide: PasswordHasher,
            useClass: BcryptHasher,
        },
    ],
    exports: [PasswordHasher],
})
export class BcryptHasherModule {}
