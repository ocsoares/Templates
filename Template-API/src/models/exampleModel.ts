import mongoose, { Schema } from "mongoose";

interface IExample {
    name: string;
    password: string;
}

export const exampleModel = mongoose.model('example', new Schema<IExample>({
    name: { type: String, required: true, unique: true },
    password: { type: String, required: true, unique: true },
},
    {
        timestamps: true
    }
));