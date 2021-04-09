var societywatchman = require('../models/societywatchman_model');
var express = require('express');
var router = express.Router();

router.post('/', function(req, res, next) {
    societywatchman.watchmanLogin(req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

module.exports = router;