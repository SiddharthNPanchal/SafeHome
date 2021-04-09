var db=require('../dbconnec');
var society={
    getAllSociety:function(callback){
        return db.query('select * from society',callback);
    },
    addSociety:function(item,callback){
        return db.query("insert into society(secretaryPhoneNumber,societyName) values(?,?)",[item.secretaryPhoneNumber,item.societyName],callback);
    },
    deleteSociety:function(id,callback){
        return db.query("delete from society where secretaryPhoneNumber=?",[id],callback);
    },
    getSocietyById:function(id,callback){
        return db.query("select * from society where secretaryPhoneNumber=?",[id],callback);
    },
    updateSocietyById:function(id,item,callback){
        return db.query("update society set societyName=? where secretaryPhoneNumber=?",[item.societyName,id],callback);
    }

};
module.exports=society;
//secretaryPhoneNumber societyName