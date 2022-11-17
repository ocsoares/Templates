import express from 'express';
import cors from 'cors';
import atlasDBConnection from './config/database';
import Logger from './config/logs';
import morganMiddleware from './middleware/morganMiddleware';

const server = express();

const localhost = 'http://localhost';
const port = 5000;

server.use(express.json());

server.use(cors());

// Middlewares
server.use(morganMiddleware);

// Padroniza TODAS as Rotas para conter /api/... na URL !! <<
server.use('/api/',
    // Rota exportada
);

server.listen(port, async () => {
    await atlasDBConnection();

    Logger.info(`Servidor rodando remotamente em ${localhost}:${port}`);
});