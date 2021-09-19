const User = require('../models/user');

const get = (req, res, next) => {

    let users;
    try {
        users = User.find();
    }
    catch (err) {
        next(err)
    }

    return res.json({ users: users });
}

const createUser = async (req, res, next) => {

    console.log("📱📱📱📱📱")
    console.log(req.body)

    const users = await User.find({ userName: req.body.userName })

    console.log("🍕")
    console.log(users)

    if(users.length != 0) {
        return res.json({error: "Username taken"})
    }
    
    const user = new User({
        userName: req.body.userName,
        bountiesAccepted: [],
        bountiesCreated: [],
        totalCash: 0
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

const getUser = async (req, res, next) => {
    // let allUsers;
    let user;
    try {
        // allUsers = await User.find();
        // console.log(req.params.name)
        user = await User.findOne({ userName: req.params.name });
        console.log(allUsers);
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
    getUser,
    createUser
};