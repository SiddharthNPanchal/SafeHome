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
// const { response } = require('../app');
var societywatchman = {
    getSocietyWatchman: function(secretaryPhoneNumber, callback) {
        return db.query("select u.userName,u.userPhoneNumber from users u,societywatchman sw where u.userTypeId=3 and u.userPhoneNumber=sw.userPhoneNumber and sw.secretaryPhoneNumber =? ", secretaryPhoneNumber, callback);
    },

    insertSocietyWatchman: function(item, callback) {
        return db.query("INSERT INTO societyWatchman(userPhoneNumber,secretaryPhoneNumber) VALUES (?,?)", [item.userPhoneNumber, item.secretaryPhoneNumber], callback);
    },

    watchmanLogin: function(item, callback) {
        console.log(item.password);
        var encpassword = encrypt(item.password);
        console.log(encpassword);
        return db.query("select u.userName,sw.secretaryPhoneNumber,s.societyName from users u,societywatchman sw,society s where u.userTypeId=? and u.userPhoneNumber=? and u.password=? and u.userPhoneNumber=sw.userPhoneNumber and sw.secretaryPhoneNumber=s.secretaryPhoneNumber", [item.userTypeId, item.userPhoneNumber, encpassword], callback);
        // return db.query("select * from users u where u.userPhoneNumber=? and u.password=? and u.userTypeId=? ", [item.userPhoneNumber, item.password, item.userTypeId], callback);
    },

    delete_watchman: function(userPhoneNumber,callback){
        return db.query("DELETE FROM societywatchman WHERE userPhoneNumber=?",userPhoneNumber,callback);
    }


};
module.exports = societywatchman;