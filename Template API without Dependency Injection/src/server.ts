import 'dotenv/config';
import mongooseConnection from './config/database';
import Logger from './config/logs';
import { app } from './app';

const host = process.env.HOST_URL;
const port = process.env.HOST_PORT;

app.listen(port, async () => {
    await mongooseConnection();

    Logger.info(`Servidor rodando remotamente em ${host}:${port}`);

    process.env.NODE_ENV ? console.log(`Servidor rodando em produção na porta ${port} !`) : '';
});