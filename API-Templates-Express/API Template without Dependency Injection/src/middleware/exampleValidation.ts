import { body } from 'express-validator';

// Utilizar esse Middleware ANTES do handleValidation !! <<

// Utilizei os isAlgumType() e NÃO o exists() porque os isAlgumType() JÁ Verifica se Existe no TIPO especificado !! <<
// OBS: Poderia checar se Existe com exists(), se NÃO, mostrar uma mensagem com withMessage() e DEPOIS checar o Tipo seguido de OUTRA Mensagem !! <<

export const exampleValidation = () => {
    return [ // Nesse caso abaixo, Verifica o campo name do body, se é String, se NÃO for, Retorna uma Mensagem no withMessage !! <<
        body('name').isString().withMessage('Insira um nome válido em string.'),
        body('age').isNumeric().withMessage('Insira uma idade válida em número.'),
        body('height').isNumeric().withMessage('Insira uma altura válida em número.'),
        body('current_team').isString().withMessage('Insira o time atual válido em string.'),
        body('rivals_team').optional().isArray().withMessage('Insira um array de times rivais !').custom((rivals_team) => {
            if (Array.isArray(rivals_team)) {
                rivals_team.forEach((rivals_team) => {
                    if (typeof rivals_team !== 'string') {
                        throw new Error('Preencha o array somente com strings !'); // Esse throw new Error('...') vai Retornar um Objeto de Erro igual aos outros, com a Mensagem no Error('...') !! <<
                    }
                });
            }
            return true; // Se passar dessas Validações (custom), esse true funciona como um next() !! <<
        })
    ];
};