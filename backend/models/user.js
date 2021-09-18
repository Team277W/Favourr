const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const userSchema = new Schema({
    userName: {
        type: String,
        required: [true, "This is a required field!"]
    }, 
    bountiesAccepted: {
        type: [Bounty],
        required: [true, "This is a required field!"]
    },
    bountiesCreated: {
        type: [Bounty],
        required: [true, "This is a required field!"]
    },
    totalCash: {
        type: Number,
        required: [true, "This is a required field!"]
    }
}, { timestamps: true });

// Model
const User = mongoose.model('User', userSchema);

module.exports = Bounty;