export interface IMongoose {
    connection(): Promise<void>;
    closeConnection(): Promise<void>;
}