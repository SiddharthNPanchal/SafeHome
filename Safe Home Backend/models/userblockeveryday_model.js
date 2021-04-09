var db=require('../dbconnec');
var userblockeveryday={
    getUserblockeveryday:function(blockNumber,flatName,secretaryPhoneNumber,callback){
        return db.query('select * from userblockeveryday u join everyday e join everydaycategory ec on e.categoryId=ec.categoryId and  e.everydayPhoneNumber=u.everydayPhoneNumber  where blockNumber=? and flatName=? and u.secretaryPhoneNumber=?',[blockNumber,flatName,secretaryPhoneNumber],callback);
    },
    deleteuserblockeveryday:function(blockNumber,flatName,secretaryPhoneNumber,everydayPhoneNumber,callback){
        return db.query('delete from userblockeveryday where blockNumber=? and flatName=? and secretaryPhoneNumber=? and everydayPhoneNumber=?',[blockNumber,flatName,secretaryPhoneNumber,everydayPhoneNumber],callback);
    }, 
    insertuserblockeveryday:function(item,callback)
    {
        return db.query("insert into userblockeveryday values(?,?,?,?)",[item.blockNumber,item.flatName,item.secretaryPhoneNumber,item.everydayPhoneNumber],callback);
    }
};
module.exports=userblockeveryday;