var everydayinout_lastactivity = require("../models/everydayinout_model");
var express = require("express");
const { getAllEverydayBySecretaryPhoneNumber } = require("../models/everyday_model");
var router = express.Router();

router.get("/:secretaryPhoneNumber/:userPhoneNumber?/:blockName/:flatNumber", function(req, res, next) {
    everydayinout_lastactivity.getAllInOutEntriesforUserS(req.params.secretaryPhoneNumber, req.params.userPhoneNumber, req.params.blockName, req.params.flatNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

router.get("/:everydayPhoneNumber", function(req, res, next) {
    everydayinout_lastactivity.giveNotification(req.params.everydayPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});
module.exports = router;