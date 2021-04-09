var everyday_model = require("../models/everyday_model");
var express = require("express");
var router = express.Router();
var multer = require('multer');
var path = require('path');
const everydayinout = require("../models/everydayinout_model");
const everyday = require("../models/everyday_model");

var storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'public/images/Everyday_images')
    },
    filename: (req, file, cb) => {
        x = file.fieldname + '-' + Date.now() + path.extname(file.originalname);
        cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname))
    }
});
var upload = multer({ storage: storage });



router.get("/:id/:secretaryPhoneNumber", function(req, res, next) {
    everyday_model.getAllEveryday(req.params.id, req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

router.get("/:id", function(req, res, next) {
    everyday_model.getEverydayById(req.params.id, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});
router.delete("/:id", function(req, res, next) {
    everyday_model.deleteEveryday(req.params.id, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.put("/:id", function(req, res, next) {
    everyday_model.updateEverydayById(req.params.id, req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.post("/", function(req, res, next) {
    everyday_model.addEveryday(req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});


// router.put('/', upload.single('Everyday_images'), function(req, res, next) {
//     everyday_model.updateEveryday_image(req.body, req.file.filename, function(err, rows) {
//         if (err) {
//             res.json(err);
//         } else {
//             res.json(rows);
//         }
//     });
// });

// router.post('/', upload.single('Everyday_images'), function(req, res, next) {
//     everyday_model.addEveryday(req.body, req.file.filename, function(err, rows) {
//         if (err) {
//             res.json(err);
//         } else {
//             res.json(rows);
//         }
//     });
// });

module.exports = router;