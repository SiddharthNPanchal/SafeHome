var db=require('../dbconnec');
var usertype={
    getAllUserType:function(callback){
        return db.query('select * from usertype',callback);
    },
    addUserType:function(item,callback){
        return db.query("insert into usertype(name) values(?)",[item.name],callback);
    },
    deleteUserType:function(id,callback){
        return db.query("delete from usertype where userTypeId=?",[id],callback);
    },
    getUserTypeById:function(id,callback){
        return db.query("select * from usertype where userTypeId=?",[id],callback);
    },
    updateUserTypeById:function(id,item,callback){
        return db.query("update usertype set name=? where userTypeId=?",[item.name,id],callback);
    },
    getUserTypeByName:function(name,callback){
        return db.query("select * from usertype where name=?",[name],callback)
    }

};
module.exports=usertype;
