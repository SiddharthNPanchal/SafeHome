var notice = require('../models/notice');
var express = require('express');
var userToken = require('../models/usertoken_model');
var router = express.Router();


router.get('/:secretaryPhoneNumber?', function(req, res, next) {
    console.log(req.params.secretaryPhoneNumber);
    notice.getLastNotice(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});
module.exports=router;