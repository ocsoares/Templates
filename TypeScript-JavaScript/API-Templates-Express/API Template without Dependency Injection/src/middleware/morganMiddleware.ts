import 'dotenv/config';
import morgan, { StreamOptions } from 'morgan';
import Logger from '../config/logs';

// Vai mostrar um Log para CADA Requisição na Aplicação (API) !! <<

const stream: StreamOptions = {
    write: (message) => Logger.http(message)
};

// Função para NÃO imprimir as Mensagens do Morgan em Produção, porque pode Demandar muito Processamento descenessário !! <<
const skipMorgan = () => {
    return process.env.NODE_ENV ? true : false;
};

const morganMiddleware = morgan(
    ':method :url :status :res[content-length] - :response-time ms',
    { stream, skip: skipMorgan }
);

export default morganMiddleware;