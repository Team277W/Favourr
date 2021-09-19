const Bounty = require('../models/bounty');
const User = require('../models/user');

const getByCity = async (req, res, next) => {
    // let allBounties;
    let cityBounties;
    try {
        // allBounties = await Bounty.find();
        cityBounties = await Bounty.find({ city: req.params.city });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        bounties: cityBounties,
        message: "Bounties in this city" 
    });
}

const createBounty = (req, res, next) => {

    const bounty = new Bounty({
        title: req.body.title,
        body: req.body.body,
        contact: req.body.contact,
        city: req.body.city,
        status: 0,
        cash: req.body.cash
    });

    try {
        bounty.save(function (err, data) {
            if(err) {
                res.status(500);
                return res.json({ error: err });
            }
            else {
                return res.json({ bounty: data });
            }
        });

        // add to users created bounties
    } 
    catch (err) {
        return res.json({ error: err });
    }
}

const getByUser = async (req, res, next) => {
    // let allBounties;
    let userBounties;
    try {
        // allBounties = await Bounty.find();
        userBounties = await Bounty.find({ userName: req.params.user });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        bounties: userBounties,
        message: "Bounties by user" 
    });
}

const updateStatus = async (req, res, next) => {
    let bountyToUpdate;

    if (req.params.level < 0 || req.params.level > 2){
        return res.json({
            error: "Invalid LEVEL!"
        })
    }
    else if(req.params.level == 2){
        User.findOne({ _id: req.params.user }, function (err, doc){
            if(err) {
                return res.json({
                    error: err
                })
            }
            else {
                try {
                    if(!doc.bountiesCreated.includes(req.params.id)){
                        return res.json({
                            error: "Invalid user permissions."
                        })
                    }
                }
                catch(err) {
                    return res.json({
                        error: err
                    })
                }
            }
        })
    }
    
    try {

        console.log("✅")
        console.log(req.params)

        bountyToUpdate = await Bounty.findByIdAndUpdate(
            req.params.id, 
            {status: req.params.level}, 
            {new: true}, 
            (err) => {return res.status(500).send(err);});

        // update user
        userToUpdate = await User.findByIdAndUpdate(
            req.params.user, 
            { $push: { bountiesCreated: req.params.id } }, 
            {new: true}, 
            (err) => {return res.status(500).send(err);});

        console.log("◻️◻️◻️◻️◻️◻️◻️◻️◻️")
        console.log(userToUpdate);

    } catch (err) {
        next(err);
    }

    return res.json({ 
        updatedBounty: bountyToUpdate,
        message: "Updated Bounty" 
    });
}

const deleteBounty = (req, res, next) => {

    Blog.findByIdAndDelete(req.params.id, function(err, data) {
        if(err) {
            next(err);
        }
        else {
            res.json({ blog: data });
        }
    });
}

module.exports = {
    createBounty,
    getByCity,
    getByUser,
    updateStatus,
    deleteBounty
};