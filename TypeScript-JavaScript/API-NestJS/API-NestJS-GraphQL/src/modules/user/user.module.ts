import { Module } from '@nestjs/common';
import { CreateUserService } from './use-cases/create-user/create-user.service';
import { LoginUserService } from './use-cases/login-user/login-user.service';
import { CreateUserResolver } from './use-cases/create-user/create-user.resolver';
import { AuthModule } from '../auth/auth.module';
import { LoginUserResolver } from './use-cases/login-user/login-user.resolver';
import { FindUserService } from './use-cases/find-user/find-user.service';
import { FindUserResolver } from './use-cases/find-user/find-user.resolver';

@Module({
    imports: [AuthModule],
    controllers: [],
    providers: [
        CreateUserService,
        CreateUserResolver,
        LoginUserService,
        LoginUserResolver,
        FindUserService,
        FindUserResolver,
    ],
})
export class UserModule {}
