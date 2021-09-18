const express = require('express');
const router = express.Router();
const controller = require('../controllers/userController');

router.get('/', controller.get);
router.get('/useraccepted/:id', controller.getUserAccepted);
router.get('/usercreated/:id', controller.getUserCreated);
router.get('/userName/:id', controller.getUserName);


// router.get('/:id', controller.detailsBounty);
// router.put('/:id', controller.editBounty);
// router.delete('/:id', controller.deleteBounty);

module.exports = router;