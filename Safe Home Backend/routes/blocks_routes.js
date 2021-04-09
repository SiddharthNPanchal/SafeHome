var block_model = require("../models/blocks_model");
var express = require("express");
var router = express.Router();



router.get("/:blockName?/:secretaryPhoneNumber?", function(req, res, next) {
  if (req.params.blockName) {
    block_model.getBlockById(req.params.blockName,req.params.secretaryPhoneNumber,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    block_model.getAllBlock(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});
router.delete("/:blockName/:secretaryPhoneNumber", function(req, res, next) {
  block_model.deleteBlock(req.params.blockName,req.params.secretaryPhoneNumber,function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:blockName/:secretaryPhoneNumber", function(req, res, next) {
  block_model.updateBlockById(req.params.blockName,req.params.secretaryPhoneNumber,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
  block_model.addBlock(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;
