var everyday_model = require("../models/everyday_model");
var express = require("express");
var router = express.Router();

router.get("/:secretaryPhoneNumber?", function(req, res, next) {
    everyday_model.getAllEverydayBySecretaryPhoneNumber(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

module.exports = router;