const Bounty = require('../models/bounty');

const get = (req, res, next) => {
    return res.json({ message: "Hello 277 Lester" });
}


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

const createBounty = async(req, res, next) => {

    const bounty = new Bounty({
        title: req.body.title,
        body: req.body.body,
        contact: req.body.contact,
        city: req.body.city,
        status: 0,
        cash: req.body.cash
    });

    bounty.save(function (err, data) {
        if(err) {
            res.status(500);
            return res.json({ error: err });
        }
        else {
            return res.json({ bounty: data });
        }
    });
}

const getByUser = async (req, res, next) => {
    // let allBounties;
    let userBounties;
    try {
        // allBounties = await Bounty.find();
        cityBounties = await Bounty.find({ userName: req.params.user });
        // console.log(allBounties);
    } catch (err) {
        next(err);
    }
        
    return res.json({ 
        bounties: userBounties,
        message: "Bounties in this city" 
    });
}

module.exports = {
    get,
    createBounty,
    getByCity,
    getByUser
};