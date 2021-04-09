var db=require('../dbconnec');
const { response } = require('../app');
var userToken={
    updateUserToken:function(id,item,callback)
    { 
        return db.query("update users set userToken=? where userPhoneNumber=?",[item.userToken,id],callback);
    },
    getUserToken:function(secretaryPhoneNumber,callback)
    {
        return db.query("select u.userToken from societyuserlogin s join users u on u.userPhoneNumber=s.userPhoneNumber where secretaryPhoneNumber=? group by u.userToken",[secretaryPhoneNumber],callback);
    }
    
};
module.exports=userToken;