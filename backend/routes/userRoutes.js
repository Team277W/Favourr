const express = require('express');
const router = express.Router();
const controller = require('../controllers/userController');

router.post('/', controller.createUser);

router.get('/accepted/:name', controller.getUserAccepted);

router.get('/created/:name', controller.getUserCreated);
router.get('/user/:name', controller.getUser);

// router.get('/:id', controller.detailsBounty);
// router.put('/:id', controller.editBounty);
// router.delete('/:id', controller.deleteBounty);

module.exports = router;