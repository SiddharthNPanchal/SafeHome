var societywatchman = require('../models/societywatchman_model');
var express = require('express');
var router = express.Router();

router.get('/:secretaryPhoneNumber?', function(req, res, next) {
    console.log(req.params.secretaryPhoneNumber);
    societywatchman.getSocietyWatchman(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.post('/', function(req, res, next) {
    console.log(req.body);
    societywatchman.insertSocietyWatchman(req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.delete('/:userPhonenumber', function(req, res, next) {
    console.log(req.body);
    societywatchman.delete_watchman(req.params.userPhonenumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});
module.exports = router;