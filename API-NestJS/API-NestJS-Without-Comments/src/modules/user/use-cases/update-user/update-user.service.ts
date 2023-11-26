import { Injectable } from '@nestjs/common';
import { IService } from 'src/interfaces/IService';
import { UserRepository } from 'src/repositories/abstracts/UserRepository';
import { UserNotFoundException } from 'src/exceptions/user-exceptions/user-not-found.exception';
import { ErrorUpdatingUserException } from 'src/exceptions/user-exceptions/error-updating-user.exception';
import { UpdateUserDTO } from './dtos/UpdateUserDTO';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';
import { PasswordHasher } from 'src/cryptography/abstracts/password-hasher';

@Injectable()
export class UpdateUserService implements IService {
    constructor(
        private readonly userRepository: UserRepository,
        private readonly passwordHasher: PasswordHasher,
    ) {}

    async execute(
        id: string,
        data: UpdateUserDTO,
    ): Promise<IUserWithoutPassword> {
        const user = await this.userRepository.findById(id);

        if (!user) {
            throw new UserNotFoundException();
        }

        if (data.password) {
            if (data.password !== user.password) {
                data.password = await this.passwordHasher.hash(
                    data.password,
                    10,
                );
            }
        }

        const updatedUser = await this.userRepository.updateOneById(id, data);

        if (!updatedUser) {
            throw new ErrorUpdatingUserException();
        }

        const updatedUserWithoutPassword: IUserWithoutPassword = {
            id: updatedUser.id,
            name: updatedUser.name,
            email: updatedUser.email,
        };

        return updatedUserWithoutPassword;
    }
}
