var everydaycategory_model = require("../models/everydaycategory_model");
var express = require("express");
var router = express.Router();

router.get("/:id?", function(req, res, next) {
    if (req.params.id) {
        everydaycategory_model.getEverydayCategoryById(req.params.id, function(err, rows) {
            if (err) {
                res.json(err);
            } else {
                res.json(rows);
            }
        });
    } else {
        everydaycategory_model.getAllEverydayCategory(function(err, rows) {
            if (err) {
                res.json(err);
            } else {
                res.json(rows);
            }
        });
    }
});

router.delete("/:id", function(req, res, next) {
    everydaycategory_model.deleteEverydayCategory(req.params.id, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.put("/:id", function(req, res, next) {
    everydaycategory_model.updateEverydayCategoryById(req.params.id, req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.post("/", function(req, res, next) {
    everydaycategory_model.addEverydayCategory(req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

module.exports = router;