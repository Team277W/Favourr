const express = require('express');
const router = express.Router();
const controller = require('../controllers/userController');

router.get('/', controller.get);
router.get('/useraccepted/:userAccepted', controller.getUserAccepted);
router.get('/usercreated/:userCreated', controller.getUserCreated);
router.get('/userName/:userName', controller.getUserName);


// router.get('/:id', controller.detailsBounty);
// router.put('/:id', controller.editBounty);
// router.delete('/:id', controller.deleteBounty);

module.exports = router;