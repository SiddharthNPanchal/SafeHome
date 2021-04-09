// var admin = require("firebase-admin");

//             var serviceAccount = require("D:/Studies/DAIICT/SI/SI/SIBackend/private.json");

//             admin.initializeApp({
//             credential: admin.credential.cert(serviceAccount),
//             databaseURL: "https://siproject-88d62.firebaseio.com"
//             });

//             //redmi note 9 //ehvYVdXCR4qzc8o_jAE6rE:APA91bH2DECzK0L5IztF5TS3CC_5uMI7xlUwR7oYl7cUS3mNNukXJ-VRgjZ1vRSuKVE6Y2jyFyh78wE6lq1cB_nViHqTSBWEJ-jaS1QzBRfdyIH19bAtgWbjKGzHBk7vlzUfyE3V9QJV'
//             //var token=['eoIkVvHVZsw:APA91bEF44kpSsKujeoOOnwn9gJ3g3QBRUh_RZ2_3NqnevTXGHuH96bQqRytgvennN2MjOm97UTzmOUhXp18poH1e0P5bAhKi6u7XQgpQBMdjSkcetEPvoe5pY-kt-pbIGeDoqqu3gB-']
            
//             var token=['d3soyopRTemLqx24AgtsYl:APA91bGaQW14YjrhoW0oMINR35t6h8LE_wAHWen3mh5zokaHxVKx6Tn0FDiWFZ3tYnTd6ZYNmSwnZB2Oe85TOZX80NGQ0wvrV_N77FufF0fxyClK6E7QhABN07oKXVp82MZlMIigmOPR']
            
//             var payload={
//                 notification:{
//                     title:"This is a notification",
//                     body:"body"
//                 }
//             };

//             var option={
//                 priority:"high",
//                 timeToLive: 60*60*24
//             };
            
//             admin.messaging().sendToDevice(token,payload,option)
//             .then(function(response)
//             {
//                 console.log("Received",response);
//             })
//             .catch(function(error)
//             {
//                 console.log("error",error);
//             });
// darshan phone token -  cJkyNddns70:APA91bE3mOC4MBM1M_QfUKTlaLq6H34zQsA8gTit8E5JcukXiagGw_pkCoZIBjrRNHW8g_IzvUa_DtTP_IA-zMwaKXKeQAv0vPvhfp3D9TVKAbEJyR5ScjRUw00nqe1lsJ__96pAb37P

// var FCM = require('fcm-node');
//     var serverKey = 'AAAAhlXDuQc:APA91bH1kyeBT4f5V5dztcYKniBxj9Day9b0ZLhkcC0tb0SMrKNqCDfSN40g6i_xbbb5CAvOnbHnHqSMAqLB-QKb-z7-LQS_eL0UYEW8vC1dm9cgInfHQyP8uY62XqZKuDbU6y3LnBQD'; // put your server key here
//     var fcm = new FCM(serverKey);
 
//     var message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
//         to: 'cJkyNddns70:APA91bE3mOC4MBM1M_QfUKTlaLq6H34zQsA8gTit8E5JcukXiagGw_pkCoZIBjrRNHW8g_IzvUa_DtTP_IA-zMwaKXKeQAv0vPvhfp3D9TVKAbEJyR5ScjRUw00nqe1lsJ__96pAb37P', 
//         collapse_key: 'green',
        
//         notification: {
//             title: 'Title of your push notification', 
//             body: 'Body of your push notification' 
//         },
        
//         data: {  //you can send only notification or only data(or include both)
//             my_key: 'my value',
//             my_another_key: 'my another value'
//         }
//     };
    
//     fcm.send(message, function(err, response){
//         if (err) {
//             console.log("Something has gone wrong!" , err);
//         } else {
//             console.log("Successfully sent with response: ", response);
//         }
//     });



var admin = require("firebase-admin");

var serviceAccount = require("D:/Studies/DAIICT/SI/SI/SIBackend/private.json");

admin.initializeApp({
credential: admin.credential.cert(serviceAccount),
databaseURL: "https://summerinternship-2cd36.firebaseio.com"
});


//redmi note 9 //ehvYVdXCR4qzc8o_jAE6rE:APA91bH2DECzK0L5IztF5TS3CC_5uMI7xlUwR7oYl7cUS3mNNukXJ-VRgjZ1vRSuKVE6Y2jyFyh78wE6lq1cB_nViHqTSBWEJ-jaS1QzBRfdyIH19bAtgWbjKGzHBk7vlzUfyE3V9QJV'
//var token=['eoIkVvHVZsw:APA91bEF44kpSsKujeoOOnwn9gJ3g3QBRUh_RZ2_3NqnevTXGHuH96bQqRytgvennN2MjOm97UTzmOUhXp18poH1e0P5bAhKi6u7XQgpQBMdjSkcetEPvoe5pY-kt-pbIGeDoqqu3gB-']

var token=['f_D5qaqmRgCclj-sQNl_E_:APA91bHMzNIG8mrqg0sPsALScqZQ6bJUwAcTPaVhwXezsltA8vx3PAPq3aXjxR5iaQvp_RZhJUWVp91qjksEqSv4qVaupa6TNIm5MEfH6ayS1DPvfv4wSjTysNECBg_yZp99nkPYnCpP']

var payload={
    notification:{
        title:"This is a notification",
        body:"body"
    }
};

var option={
    priority:"high",
    timeToLive: 60*60*24
};

admin.messaging().sendToDevice(token,payload,option)
.then(function(response)
{
    console.log("Received",response);
})
.catch(function(error)
{
    console.log("error",error);
});