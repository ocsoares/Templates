import { MongooseODM } from "../config/Database";

// Utilizando no arquivo server !!! <<<
export const makeMongooseODMFactory = (): MongooseODM => {
    const atlasDatabase = new MongooseODM();
    return atlasDatabase;
};