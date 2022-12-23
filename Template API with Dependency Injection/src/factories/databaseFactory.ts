import { Mongoose } from "../config/Database";

// Utilizando no server !!! <<<
export const makeAtlasDatabaseFactory = (): Mongoose => {
    const atlasDatabase = new Mongoose();
    return atlasDatabase;
};