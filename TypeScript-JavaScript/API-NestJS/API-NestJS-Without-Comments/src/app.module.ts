import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { UserModule } from './modules/user/user.module';
import { AuthModule } from './modules/auth/auth.module';
import { APP_GUARD } from '@nestjs/core';
import { JwtAuthGuard } from './modules/auth/guards/jwt-auth.guard';
import { LoginValidationBodyModule } from './modules/login-validation-body/login-validation-body.module';
import { PrismaDatabaseModule } from './repositories/implementations/prisma/prisma-database.module';
import { BcryptHasherModule } from './cryptography/implementations/bcrypt/bcrypt-hasher.module';
import { JwtManagerModule } from './cryptography/implementations/jwt/jwt-manager.module';

@Module({
    imports: [
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: '.env',
        }),
        PrismaDatabaseModule,
        JwtManagerModule,
        BcryptHasherModule,
        UserModule,
        AuthModule,
        LoginValidationBodyModule,
    ],
    providers: [
        {
            provide: APP_GUARD,
            useClass: JwtAuthGuard,
        },
    ],
})
export class AppModule {}
