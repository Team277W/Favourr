const User = require('../models/user');

const get = (req, res, next) => {
    return res.json({ message: "Hello 277 Lester" });
}

const createUser = async (req, res, next) => {
    
    const user = new User({
        userName: req.body.userName,
        bountiesAccepted: req.body.bountiesAccepted,
        bountiesCreated: req.body.bountiesCreated,
        status: 0,
        cash: req.body.cash
    });

    user.save(function (err, data) {
        if(err) {
            res.status(500);
            return res.json({ error: err });
        }
        else {
            return res.json({ user: data });
        }
    });
}

const getUserName = async (req, res, next) => {
    // let allBounties;
    let user;
    try {
        // allBounties = await Bounty.find();
        userName = await User.findById(id);
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        userName: user.userName,
        message: "Username" 
    });
}

const getUserAccepted = async (req, res, next) => {
    // let allBounties;
    let user;
    try {
        // allBounties = await Bounty.find();
        user = await User.findById(id);
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
        user = await User.findById(id);
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
    getUserName
};