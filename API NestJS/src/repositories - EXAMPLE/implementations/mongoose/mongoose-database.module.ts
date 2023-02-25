import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { PayablesRepository } from '../../abstracts/PayablesRepository';
import { TransactionRepository } from '../../abstracts/TransactionRepository';
import { UserRepository } from '../../abstracts/UserRepository';
import { MongoosePayablesRepository } from './payables/MongoosePayablesRepository';
import { Payables, PayablesSchema } from './schemas/payables.schema';
import { Transaction, TransactionSchema } from './schemas/transaction.schema';
import { User, UserSchema } from './schemas/user.schema';
import { MongooseTransactionRepository } from './transaction/MongooseTransactionRepository';
import { MongooseUserRepository } from './user/MongooseUserRepository';

@Module({
    imports: [
        // NÃO coloquei a conexão do Mongoose aqui porque como esse Módulo será importado DENTRO dos
        // Módulos da Aplicação isso iria fazer com que os Testes Conectassem
        // NESSE Banco de Dados AQUI, ao invés do In Memory !!!
        MongooseModule.forFeature([{ name: User.name, schema: UserSchema }]),
        // others...
    ],
    providers: [
        {
            provide: UserRepository,
            useClass: MongooseUserRepository,
        },
        // others...
    ],
exports: [UserRepository,/* others... */ ],
})
export class MongooseDatabaseModule {}
