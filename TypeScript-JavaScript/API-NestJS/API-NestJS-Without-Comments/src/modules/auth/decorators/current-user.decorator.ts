import { createParamDecorator, ExecutionContext } from '@nestjs/common';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';
import { IAuthRequest } from '../types/IAuthRequest';

export const CurrentUser = createParamDecorator(
    (data: unknown, context: ExecutionContext): IUserWithoutPassword => {
        const request = context.switchToHttp().getRequest<IAuthRequest>();

        return request.user;
    },
);
