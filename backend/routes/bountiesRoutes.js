const express = require('express');
const router = express.Router();
const controller = require('../controllers/bountiesController');

router.post('/', controller.createBounty);

router.get('/city/:city', controller.getByCity);
router.get('/user/:user', controller.getByUser);

router.put('/su/:level/id/:id', controller.updateStatus);
// router.get('/:id', controller.detailsBounty);
// router.put('/:id', controller.editBounty);
// router.delete('/:id', controller.deleteBounty);

module.exports = router;