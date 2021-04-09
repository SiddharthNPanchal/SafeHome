var db = require('../dbconnec');
const crypto = require('crypto');

function encrypt(text) {
    var cipher = crypto.createCipher('aes-256-cbc', 'd6F3Efeq')
    var crypted = cipher.update(text, 'utf8', 'hex')
    crypted += cipher.final('hex');
    return crypted;
}

function decrypt(text) {
    var decipher = crypto.createDecipher('aes-256-cbc', 'd6F3Efeq')
    var dec = decipher.update(text, 'hex', 'utf8')
    dec += decipher.final('utf8');
    return dec;
}

var users = {
    getAllUsers: function(callback) {
        return db.query('select u.userPhoneNumber,u.userName,u.password,ut.name from users u join usertype ut on u.userTypeId=ut.userTypeId', callback);
    },
    addUsers: function(item, callback) {
        console.log(item.password);
        var encpassword = encrypt(item.password);
        console.log(encpassword);
        return db.query("insert into users(userPhoneNumber,userName,password,userTypeId) values(?,?,?,?)", [item.userPhoneNumber, item.userName, encpassword, item.userTypeId], callback);
    },
    deleteUsers: function(id, callback) {
        return db.query("delete from users where userPhoneNumber=?", [id], callback);
    },
    getUsersById: function(id, callback) {
        return db.query("select * from users where userPhoneNumber=?", [id], callback);
    },
    updateUsersById: function(id, item, callback) {
        return db.query("update users set userName=?,password=?,userTypeId=? where userPhoneNumber=?", [item.userName, item.password, item.userTypeId, id], callback);
    },
    login: function(username, password, userType, callback) {
        return db.query("SELECT * FROM users join usertype on users.userTypeId=usertype.userTypeId WHERE userPhoneNumber=? and PASSWORD=? and usertype.name=?", [username, password, userType], callback);

        //return db.query("select * from users where userPhoneNumber=? and password=?",[username,password],callback);
    },
    loginUsingOTP: function(username, userType, callback) {
        return db.query("SELECT * FROM users join usertype on users.userTypeId=usertype.userTypeId WHERE userPhoneNumber=? and usertype.name=?", [username, userType], callback);

        //return db.query("select * from users where userPhoneNumber=? and password=?",[username,password],callback);
    },
    loginPost: function(item, callback) {
        var encpassword = encrypt(item.password);
       
        return db.query("SELECT * FROM users join usertype on users.userTypeId=usertype.userTypeId WHERE userPhoneNumber=? and PASSWORD=? and usertype.name=?", [item.username, encpassword, item.userType], callback);

        //return db.query("select * from users where userPhoneNumber=? and password=?",[username,password],callback);
    }

};
module.exports = users;
//userPhoneNumber userName password userTypeId