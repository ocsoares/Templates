import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { UserModule } from './modules/user/user.module';
import { AuthModule } from './modules/auth/auth.module';
import { PrismaDatabaseModule } from './repositories/implementations/prisma/prisma-database.module';
import { GraphQLModule } from '@nestjs/graphql';
import { join } from 'path';
import { ApolloDriver, ApolloDriverConfig } from '@nestjs/apollo';
import { BcryptHasherModule } from './cryptography/implementations/bcrypt/bcrypt-hasher.module';
import { JwtManagerModule } from './cryptography/implementations/jwt/jwt-manager.module';

@Module({
    imports: [
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: '.env',
        }),
        GraphQLModule.forRoot<ApolloDriverConfig>({
            driver: ApolloDriver,
            autoSchemaFile: join(process.cwd(), 'src/schema.gql'),
        }),
        PrismaDatabaseModule,
        JwtManagerModule,
        BcryptHasherModule,
        UserModule,
        AuthModule,
    ],
})
export class AppModule {}
