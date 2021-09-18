const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const userSchema = new Schema({
    // _id: mongoose.ObjectId,
    userName: {
        type: String,
        required: [true, "This is a required field!"]
    }, 
    bountiesAccepted: {
        type: [mongoose.ObjectId],
        required: [true, "This is a required field!"]
    },
    bountiesCreated: {
        type: [mongoose.ObjectId],
        required: [true, "This is a required field!"]
    }
}, { timestamps: true });

// Model
const User = mongoose.model('User', userSchema);

module.exports = User;