import { Injectable } from '@nestjs/common';
import { IService } from 'src/interfaces/IService';
import { UserRepository } from 'src/repositories/abstracts/UserRepository';
import { CreateUserDTO } from './dtos/CreateUserDTO';
import { UserAlreadyExistsByNameException } from 'src/exceptions/user-exceptions/user-already-exists-by-name.exception';
import { UserAlreadyExistsByEmailException } from 'src/exceptions/user-exceptions/user-already-exists-by-email.exception';
import { IUser } from 'src/models/IUser';
import { EncryptPasswordHelper } from 'src/helpers/encrypt-password.helper';
import { ErrorCreatingUserException } from 'src/exceptions/user-exceptions/error-creating-user.exception';

@Injectable()
export class CreateUserService implements IService {
    constructor(private readonly userRepository: UserRepository) {}

    async execute(data: CreateUserDTO): Promise<IUser> {
        const userAlreadyExistsByName = await this.userRepository.findByName(
            data.name,
        );

        if (userAlreadyExistsByName) {
            throw new UserAlreadyExistsByNameException();
        }

        const userAlreadyExistsByEmail = await this.userRepository.findByEmail(
            data.email,
        );

        if (userAlreadyExistsByEmail) {
            throw new UserAlreadyExistsByEmailException();
        }

        const createdUser = await this.userRepository.create({
            ...data,
            password: await EncryptPasswordHelper.bcryptEncrypt(
                data.password,
                10,
            ),
        });

        if (!createdUser) {
            throw new ErrorCreatingUserException();
        }

        return createdUser;
    }
}
