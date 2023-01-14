import 'dotenv/config';
import mongoose from 'mongoose';
import { IMongoose } from '../@types/interfaces/IMongoose';
import Logger from './logs';

// IMPORTANTE: A senha do Atlas em URL PRECISA Retirar os <> !! <<

export class MongooseODM implements IMongoose {
    private readonly _atlasURLConnection = process.env.ATLAS_URL_CONNECTION as string;

    async connection(): Promise<void> {
        try {
            await mongoose.connect(this._atlasURLConnection);
            Logger.info('Conectado com sucesso ao Atlas !');
        }
        catch (error: any) {
            Logger.error(error);
            Logger.error('Não foi possível conectar ao Atlas !');
            process.exit(1); // CRASHA o App INTENCIONALMENTE se NÃO conectar ao banco de dados !! <<
        }
    }

    async closeConnection(): Promise<void> {
        await mongoose.connection.close();
    }
}