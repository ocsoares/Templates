export abstract class TokenManager {
    abstract generate(payload: object, expiresIn?: string): Promise<string>;
    abstract verify(token: string): Promise<object>;
}
