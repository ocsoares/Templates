import { MongooseODM } from "./Database";
import { makeMongooseODMFactory } from '../factories/databaseFactory';

beforeAll(async () => {
    const database = makeMongooseODMFactory();

    await database.connection();
});