import { Body, Controller, Param, Patch } from '@nestjs/common';
import { IController, returnHandle } from 'src/interfaces/IController';
import { UpdateUserService } from './update-user.service';
import { UpdateUserDTO } from './dtos/UpdateUserDTO';

@Controller()
export class UpdateUserController implements IController {
    constructor(private readonly updateUserService: UpdateUserService) {}

    @Patch('user/:id')
    async handle(
        @Param('id') id: string,
        @Body() body: UpdateUserDTO,
    ): Promise<returnHandle> {
        const updatedUser = await this.updateUserService.execute(id, body);

        return {
            data: updatedUser,
        };
    }
}
