const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const bountySchema = new Schema({
    user: {
        type: mongoose.ObjectId
    },
    title: {
        type: String,
        required: [true, "This is a required field!"]
    },
    body: {
        type: String,
        required: [true, "This is a required field!"]
    },
    contact: {
        type: String,
        required: [true, "This is a required field!"]
    },
    city: {
        type: String,
        required: [true, "This is a required field!"]
    },
    cash: {
        type: Number,
        required: [true, "This is a required field!"]
    }
}, { timestamps: true });

// Model
const Bounty = mongoose.model('Bounty', bountySchema);

module.exports = Bounty;