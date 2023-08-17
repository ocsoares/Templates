import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { IUserWithoutPassword } from 'src/models/IUserWithoutPassword';
import { IService } from 'src/interfaces/IService';
import { IUserPayload } from 'src/modules/auth/models/IUserPayload';

@Injectable()
export class LoginUserService implements IService {
    constructor(private readonly jwtService: JwtService) {}

    async execute(data: IUserWithoutPassword): Promise<string> {
        const payload: IUserPayload = {
            sub: data.id,
            name: data.name,
            email: data.email,
        };

        const JWT = this.jwtService.sign(payload);

        return JWT;
    }
}
