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

const createBounty = async (req, res, next) => {

    const bounty = new Bounty({
        user: req.body.user,
        title: req.body.title,
        body: req.body.body,
        contact: req.body.contact,
        city: req.body.city,
        status: 0,
        cash: req.body.cash
    });

    let userToUpdate;
    let newBounty;
    try {
        bounty.save(async function (err, data) {
            if(err) {
                res.status(500);
                return res.json({ error: err });
            }
            else {
                newBounty = data;

                // add to users created bounties
                userToUpdate = await User.findByIdAndUpdate(
                    req.body.user, 
                    { $push: { bountiesCreated: newBounty._id } }, 
                    {new: true}, 
                    (err) => {return res.status(500).send(err);});
            }
        });
    } 
    catch (err) {
        return res.json({ error: err });
    }

    return res.json({
        bounty: newBounty
    })
}

// created
const getCreatedBounties = async (req, res, next) => {

    console.log("GET BY USER")
    // let allBounties;
    let userBounties;
    let user;
    try {

        console.log("⚙️")
        console.log(req.params.user)
        user = await User.findById(req.params.user);
        console.log(user)

        // allBounties = await Bounty.find();
        userBounties = await Bounty.find({ user: req.params.user });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        bounties: userBounties,
        totalCash: `${user.totalCash}`
    });
}

// accepted
const getAcceptedBounties = async (req, res, next) => {

    console.log("GET ACCEPTED")
    // let allBounties;
    let user;
    let bountyList = [];
    try {

        console.log("⚙️")
        console.log(req.params.user)
        user = await User.findById(req.params.user);
        console.log(user)

        let ids = [];

        (user.bountiesAccepted).forEach(id => {
            ids.push(id.toString())
        });

        console.log(ids)

        for(let i = 0; i < ids.length; i++) {
            id = ids[i]
            let bounty = await Bounty.findById(id);
            console.log("👇🏽")
            console.log(bounty)
            bountyList.push(bounty)
        }

        console.log("✅")
        console.log(bountyList)

        return res.json({ 
            bounties: bountyList,
            totalCash: `${user.totalCash}`
        });
    } catch (err) {
        console.log("2️⃣")
        console.log(err)
        return res.json({ error: err });
    }
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
            { $push: { bountiesAccepted: req.params.id } }, 
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
    getCreatedBounties,
    getAcceptedBounties,
    updateStatus,
    deleteBounty
};