export abstract class PasswordHasher {
    abstract hash(plainPassword: string, salt?: number): Promise<string>;
    abstract compare(
        plainPassword: string,
        hashedPassword: string,
    ): Promise<boolean>;
}
