import 'dotenv/config';
import mongoose from 'mongoose';
import Logger from './logs';

// IMPORTANTE: A senha do Atlas em URL PRECISA Retirar os <> !! <<

const mongooseConnection = async (): Promise<void> => {
    try {
        await mongoose.connect(process.env.ATLAS_URL as string);
        Logger.info('Conectado com sucesso ao Atlas !');
    }
    catch (error: any) {
        Logger.error(error);
        Logger.error('Não foi possível conectar ao Atlas !');
        process.exit(1); // CRASHA o App INTENCIONALMENTE se NÃO conectar ao banco de dados !! <<
    }
};

export default mongooseConnection;