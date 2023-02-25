import { Module } from '@nestjs/common';
import { UserRepository } from '../../abstracts/UserRepository';
import { PrismaService } from './prisma-client.service';
import { PrismaUserRepository } from './user/PrismaUserRepository';

@Module({
    imports: [],
    providers: [
        PrismaService,
        {
            provide: UserRepository,
            useClass: PrismaUserRepository,
        },
        // others...
    ],
exports: [UserRepository, /* others... */],
})
export class PrismaDatabaseModule {}
