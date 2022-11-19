import 'dotenv/config';
import 'express-async-errors';
import express from 'express';
import cors from 'cors';
import atlasDBConnection from './config/database';
import Logger from './config/logs';
import morganMiddleware from './middleware/morganMiddleware';
import { errorAPIMiddleware, pageNotFound } from './middleware/errorAPIMiddleware';

const server = express();

const host = process.env.HOST_URL;
const port = process.env.HOST_PORT;

server.use(express.json());

server.use(cors());

// Middlewares
server.use(morganMiddleware);

// Padroniza TODAS as Rotas para conter /api/... na URL !! <<
server.use('/api/',
    // Rota exportada
);

server.use(pageNotFound);

// Para Funções ASSÍNCRONAS (async) PRECISA usar a lib 'express-async-errors' !! <<
server.use(errorAPIMiddleware);

server.listen(port, async () => {
    await atlasDBConnection();

    Logger.info(`Servidor rodando remotamente em ${host}:${port}`);
});