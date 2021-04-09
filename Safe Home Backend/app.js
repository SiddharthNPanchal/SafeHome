var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

let userType = require('./routes/usertype_routes');
let userss = require('./routes/users_routes');
let society = require('./routes/society_routes');
let blocks = require('./routes/blocks_routes');
let flat = require('./routes/flat_routes');
let userflat = require('./routes/userflat_routes');
let everydaycategory = require('./routes/everydaycategory_routes');
let everyday = require('./routes/everyday_routes');
let usertypename = require('./routes/usertype_name_routes');
let flatSecretary = require('./routes/flat_routes2');
let userblockroutes2 = require('./routes/userblock_routes2');
let notice = require('./routes/notice_route');
let cayegory = require('./routes/category_route');
let userblockeveryday = require('./routes/userblockeveryday_route');
let userToken = require('./routes/usertoken_route');
let usersociety = require('./routes/usersocietylogin_route');
let societywatchman = require('./routes/societywatchman_route');
const societyWatchman_login = require('./routes/societywatchman_login_routes');
const everydaylist_routes = require('./routes/everydaylist_routes');
const everydayinout = require('./routes/everydayinout_routes');
const everydayinout_inentries = require('./routes/everydayinout_inentries_routes');
const everydayinout_outentries = require('./routes/everydayinout_outentries_routes');
const everydayinout_lastactivity = require("./routes/everydayinout_lastactivity_routes");
let noticeLast = require('./routes/notice_last_routes');
let loginPost = require('./routes/user_secretary_login_routes');
let updatepassword=require('./routes/update_password');








var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));






app.use('/', indexRouter);
app.use('/users', usersRouter);

app.use('/usertype', userType);
app.use('/userss', userss);
app.use('/society', society);
app.use('/blocks', blocks);
app.use('/flat', flat);
app.use('/userflat', userflat);
app.use('/everydaycategory', everydaycategory);
app.use('/everyday', everyday);
app.use('/usertypename', usertypename);
app.use('/flatsecretary', flatSecretary);
app.use('/userblock2', userblockroutes2);
app.use('/getNotice', notice);
app.use('/getCategory', cayegory);
app.use('/getuserblockeveryday', userblockeveryday);
app.use('/userToken', userToken);
app.use('/usersocietylogin', usersociety);

app.use('/societyWatchman', societywatchman);
app.use('/societywatchmanlogin', societyWatchman_login);
app.use('/everydaylist', everydaylist_routes);
app.use('/everydayinout', everydayinout);
app.use('/everydayinentries', everydayinout_inentries);
app.use('/everydayoutentries', everydayinout_outentries);
app.use('/everydayinoutlastactivity', everydayinout_lastactivity);
app.use('/noticeLast',noticeLast);
app.use('/loginPost',loginPost);
app.use('/updatepassword',updatepassword);










// catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});
var admin = require("firebase-admin");


var serviceAccount = require("./private.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://summerinternship-2cd36.firebaseio.com"
});

module.exports = app;
