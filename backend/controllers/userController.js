const User = require('../models/user');

const get = (req, res, next) => {
    return res.json({ message: "Hello 277 Lester" });
}

const getUser = async (req, res, next) => {
    // let allBounties;
    let user;
    try {
        // allBounties = await Bounty.find();
        user = await User.findOne({ userName: req.params.name });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        user: user,
        message: "Username" 
    });
}

const getUserAccepted = async (req, res, next) => {
    // let allBounties;
    let user;
    try {
        // allBounties = await Bounty.find();
        user = await User.findOne({ userName: req.params.name });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        userBountiesAccepted: user.bountiesAccepted,
        message: "Username" 
    });
}

const getUserCreated = async (req, res, next) => {
    // let allBounties;
    let user;
    try {
        // allBounties = await Bounty.find();
        user = await User.findOne({ userName: req.params.name });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        userBountiesCreated: user.bountiesCreated,
        message: "Username" 
    });
}

module.exports = {
    get,
    getUserAccepted,
    getUserCreated,
    getUser
};