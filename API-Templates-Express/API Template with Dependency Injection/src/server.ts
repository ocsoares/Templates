import 'dotenv/config';
import Logger from './config/logs';
import { app } from './app';
import { makeMongooseODMFactory } from './factories/databaseFactory';

const host = process.env.HOST_URL;
const port = process.env.HOST_PORT;

app.listen(port, async () => {
    const database = makeMongooseODMFactory();

    await database.connection();

    Logger.info(`Servidor rodando remotamente em ${host}:${port}`);

    process.env.NODE_ENV ? console.log(`Servidor rodando em produção na porta ${port} !`) : '';
});