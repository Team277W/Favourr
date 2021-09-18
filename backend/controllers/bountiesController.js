const Bounty = require('../models/bounty');

const get = (req, res, next) => {
    return res.json({ message: "Hello 277 Lester" });
}

module.exports = {
    get
};