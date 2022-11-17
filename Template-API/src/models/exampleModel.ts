import mongoose, { Schema } from "mongoose";

export const exampleModel = mongoose.model('example', new Schema({
    example: { type: String, required: true, unique: true },
},
    {
        timestamps: true
    }
));