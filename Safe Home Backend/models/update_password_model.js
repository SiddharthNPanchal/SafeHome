var db=require('../dbconnec');
const crypto = require('crypto');

function encrypt(text) {
    var cipher = crypto.createCipher('aes-256-cbc', 'd6F3Efeq')
    var crypted = cipher.update(text, 'utf8', 'hex')
    crypted += cipher.final('hex');
    return crypted;
}



var users={
    getuserpassword:function(userPhoneNumber,userType,callback){
        return db.query("SELECT password FROM users join usertype on users.userTypeId=usertype.userTypeId WHERE userPhoneNumber=?  and usertype.name=?",[userPhoneNumber,userType],callback); 

    },    
    updatepassword:function(userPhoneNumber,userTypeId,item,callback){
        var encpassword = encrypt(item.password);

        return db.query("update users set password=? where userPhoneNumber=? and userTypeId=?",[encpassword,userPhoneNumber,userTypeId],callback);
    },
};
module.exports=users;