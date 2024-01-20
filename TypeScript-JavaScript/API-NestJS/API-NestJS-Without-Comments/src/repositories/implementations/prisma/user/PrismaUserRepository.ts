import { Injectable } from '@nestjs/common';
import { IUser } from 'src/models/IUser';
import { UserRepository } from 'src/repositories/abstracts/UserRepository';
import { PrismaService } from '../prisma-client.service';
import { CreateUserDTO } from 'src/modules/user/use-cases/create-user/dtos/CreateUserDTO';
import { UpdateUserDTO } from 'src/modules/user/use-cases/update-user/dtos/UpdateUserDTO';

@Injectable()
export class PrismaUserRepository implements UserRepository {
    constructor(private readonly prismaService: PrismaService) {}

    async create(data: CreateUserDTO): Promise<IUser> {
        const createUser = await this.prismaService.user.create({ data });

        return createUser;
    }

    async findByEmail(email: string): Promise<IUser> {
        const findByEmail = await this.prismaService.user.findUnique({
            where: { email },
        });

        return findByEmail;
    }

    async findById(id: string): Promise<IUser> {
        const findById = await this.prismaService.user.findUnique({
            where: { id },
        });

        return findById;
    }

    async findByName(name: string): Promise<IUser> {
        const findByName = await this.prismaService.user.findUnique({
            where: { name },
        });

        return findByName;
    }

    async findAllUsers(): Promise<IUser[]> {
        const findAllUsers = await this.prismaService.user.findMany();

        return findAllUsers;
    }

    async deleteOneById(id: string): Promise<IUser> {
        const deleteOneById = await this.prismaService.user.delete({
            where: { id },
        });

        return deleteOneById;
    }

    async updateOneById(id: string, data: UpdateUserDTO): Promise<IUser> {
        const updateOneById = await this.prismaService.user.update({
            where: {
                id,
            },
            data,
        });

        return updateOneById;
    }
}
