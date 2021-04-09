var db=require('../dbconnec');
const { response } = require('../app');
var userSociety={
    addusersociety:function(item,callback)
    { 
        return db.query("insert into societyuserlogin values(?,?)",[item.secretaryPhoneNumber,item.userPhoneNumber],callback);
    },
    deleteusersociety:function(secretaryPhoneNumber,userPhoneNumber,callback)
    {
        return db.query("delete from societyuserlogin where secretaryPhoneNumber=? and userPhoneNumber=?",[secretaryPhoneNumber,userPhoneNumber],callback);
    }
    
};
module.exports=userSociety;