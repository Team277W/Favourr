const express = require('express');
const router = express.Router();
const controller = require('../controllers/bountiesController');

router.get('/', controller.get);
// router.post('/', controller.createBounty);

// router.get('/:id', controller.detailsBounty);
// router.put('/:id', controller.editBounty);
// router.delete('/:id', controller.deleteBounty);

module.exports = router;