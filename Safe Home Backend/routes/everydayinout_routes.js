var everydayinout = require("../models/everydayinout_model");
var express = require("express");
var router = express.Router();

router.get("/:secretaryPhoneNumber?", function (req, res, next) {
    everydayinout.getAllInOutEnteryofSociety(req.params.secretaryPhoneNumber, function (err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});


// in Entry of everyday
router.post('/', function (req, res, next) {
    everydayinout.addEverydayInEntries(req.body, function (err, rows) {
        if (err) {
            res.json(err);
        } else {

            var name;
            console.log("else");
            
            // Notifiaction code starts
            everydayinout.geteverydayname(req.body.phoneNumber,function(err,res)
            {
                console.log(req.body.phoneNumber + " hello");
                if(err)
                {
                     console.log(err);
                } 
                else
                {
                    console.log(res);
                    console.log(res[0].name + " hello");
                    name=res[0].name;
                }
            });

            


            var admin = require("firebase-admin");
            var token1 = [];
            everydayinout.giveNotification(req.body.phoneNumber, function (err, res) {
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
                            title:name,
                            body: "Has checked in"
                        }
                    };

                    var option = {
                        priority: "high",
                        timeToLive: 60 * 60 * 24
                    };

                    admin.messaging().sendToDevice(token1, payload, option)
                        .then(function (response) {
                            console.log("Received", response);
                        })
                        .catch(function (error) {
                            console.log("error", error);
                        });
                    console.log(token1 + " HELLO");
                }
            });
            console.log(token1 + " world");
            // notification code ends




            res.json(rows);
        }
    });
});


//out Entry of everyday
router.put('/:phoneNumber?', function (req, res, next) {
    everydayinout.addEverydayOutEntries(req.params.phoneNumber, function (err, rows) {
        if (err) {
            res.json(err);
        } else {

            var name;

           // Notifiaction code starts
           everydayinout.geteverydayname(req.params.phoneNumber,function(err,res)
           {
               console.log(req.params.phoneNumber + " hello");
               if(err)
               {
                    console.log(err);
               } 
               else
               {
                   console.log(res);
                   console.log(res[0].name + " hello");
                   name=res[0].name;
               }
           });

           


           var admin = require("firebase-admin");
           var token1 = [];
           everydayinout.giveNotification(req.params.phoneNumber, function (err, res) {
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
                           title:name,
                           body: "Has checked out"
                       }
                   };

                   var option = {
                       priority: "high",
                       timeToLive: 60 * 60 * 24
                   };

                   admin.messaging().sendToDevice(token1, payload, option)
                       .then(function (response) {
                           console.log("Received", response);
                       })
                       .catch(function (error) {
                           console.log("error", error);
                       });
                   console.log(token1 + " HELLO");
               }
           });
           console.log(token1 + " world");
           // notification code ends



            res.json(rows);
        }
    });
});

module.exports = router;