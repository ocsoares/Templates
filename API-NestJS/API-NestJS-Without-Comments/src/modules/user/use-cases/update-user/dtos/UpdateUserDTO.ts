import { PartialType } from '@nestjs/mapped-types';
import { CreateUserDTO } from '../../create-user/dtos/CreateUserDTO';

export class UpdateUserDTO extends PartialType(CreateUserDTO) {}
