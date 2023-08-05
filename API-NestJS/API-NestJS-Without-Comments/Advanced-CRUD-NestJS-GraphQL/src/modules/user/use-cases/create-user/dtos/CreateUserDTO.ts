import { IsEmail, IsNotEmpty, IsString, Length } from 'class-validator';

export class CreateUserDTO {
    @IsNotEmpty({ message: 'O campo name não pode ser vazio !' })
    @IsString()
    name: string;

    @IsNotEmpty()
    @IsEmail(undefined, { message: 'Insira um email válido !' })
    email: string;

    @IsNotEmpty()
    @IsString()
    @Length(7, 120)
    password: string;
}
