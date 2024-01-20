// O nome dessa Interface poderia ser ICreateUserRequest !!! <<<
// OBS: Isso serve para pegar APENAS a request do usuário, SEM o id !! <<
export interface IAnyRequest {
    username: string;
    email: string;
    password: string;
}