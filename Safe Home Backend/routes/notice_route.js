var notice = require('../models/notice');
var express = require('express');
var userToken = require('../models/usertoken_model');
var router = express.Router();


router.get('/:secretaryPhoneNumber?', function(req, res, next) {
    console.log(req.params.secretaryPhoneNumber);
    notice.getNotice(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.post('/', function(req, res, next) {
    notice.insertNotice(req.body, function(err, rows) {
        if (err) {

            res.json(err);


        } else {

            var admin = require("firebase-admin");

            //var serviceAccount = require("D:/Studies/DAIICT/SI/SI/SIBackend/private.json");

            // admin.initializeApp({
            // credential: admin.credential.cert(serviceAccount),
            // databaseURL: "https://summerinternship-2cd36.firebaseio.com"
            // });


            //redmi note 9 //ehvYVdXCR4qzc8o_jAE6rE:APA91bH2DECzK0L5IztF5TS3CC_5uMI7xlUwR7oYl7cUS3mNNukXJ-VRgjZ1vRSuKVE6Y2jyFyh78wE6lq1cB_nViHqTSBWEJ-jaS1QzBRfdyIH19bAtgWbjKGzHBk7vlzUfyE3V9QJV'

            var token = ['eoIkVvHVZsw:APA91bEF44kpSsKujeoOOnwn9gJ3g3QBRUh_RZ2_3NqnevTXGHuH96bQqRytgvennN2MjOm97UTzmOUhXp18poH1e0P5bAhKi6u7XQgpQBMdjSkcetEPvoe5pY-kt-pbIGeDoqqu3gB-']
                // var token=[];
            var token1 = [];
            userToken.getUserToken(req.body.secretaryPhoneNumber, function(err, res) {
                if (err) {
                    console.log(err);
                }
                if (res) {
                    //console.log(res);
                    res.map((x) => {
                        if (x.userToken != '')
                            token1.push(x.userToken)

                    });
                    //token = res;
                    var payload = {
                        notification: {
                            title: req.body.title,
                            body: req.body.message
                        }
                    };

                    var option = {
                        priority: "high",
                        timeToLive: 60 * 60 * 24
                    };

                    admin.messaging().sendToDevice(token1, payload, option)
                        .then(function(response) {
                            console.log("Received", response);
                        })
                        .catch(function(error) {
                            console.log("error", error);
                        });
                    console.log(token1 + " HELLO");
                }
            });
            console.log(token1 + " world");

            res.json(rows);
        }
    });
});

router.put('/:id', function(req, res, next) {
    notice.updateNotice(req.params.id, req.body, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

router.delete('/:id', function(req, res, next) {
    notice.deleteNotice(req.params.id, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });
});

module.exports = router;