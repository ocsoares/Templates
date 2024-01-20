import { Body, Controller, Post } from '@nestjs/common';
import { IController, returnHandle } from 'src/interfaces/IController';
import { CreateUserService } from './create-user.service';
import { CreateUserDTO } from './dtos/CreateUserDTO';
import { IsPublic } from 'src/modules/auth/decorators/is-public.decorator';

@Controller('auth')
export class CreateUserController implements IController {
    constructor(private readonly createUserService: CreateUserService) {}

    @IsPublic()
    @Post('register')
    async handle(@Body() body: CreateUserDTO): Promise<returnHandle> {
        const createdUser = await this.createUserService.execute(body);

        return {
            data: createdUser,
        };
    }
}
