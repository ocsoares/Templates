import { BadRequestErrorMessages } from "../@types/errorAPIMessages";

export class ErrorAPIHelper extends Error {
    public readonly statusCode: number;

    constructor(message: string, statusCode: number) {
        super(message);

        this.statusCode = statusCode;
    }
}

export class BadRequestAPIError extends ErrorAPIHelper {
    // O statusCode NÃO entrou no constructor porque, nesse caso, ele vai
    // ser FIXO, e a message vai ser passada como Parâmetro !! <<

    constructor(message: BadRequestErrorMessages) {
        super(message, 400);

        this.name = 'BadRequestError';
    }
}

// more errors...