var db=require('../dbconnec');
var flat={
    getAllflat:function(callback){
        return db.query('select * from flat',callback);
    },
    addflat:function(item,callback){
        return db.query("insert into flat(flatNumber,blockName,secretaryPhoneNumber) values(?,?,?)",[item.flatNumber,item.blockName,item.secretaryPhoneNumber],callback);
    },
    deleteflat:function(flatNumber,id,secretaryPhoneNumber,callback){
        return db.query("delete from flat where blockName=? and secretaryPhoneNumber=? and flatNumber=?",[id,secretaryPhoneNumber,flatNumber],callback);
    },
    getflatById:function(id,secretaryPhoneNumber,callback){
        return db.query("select * from flat where blockName=? and secretaryPhoneNumber=?",[id,secretaryPhoneNumber],callback);
    },
    updateflatById:function(flatNumber,id,secretaryPhoneNumber,item,callback){
        return db.query("update flat set flatNumber=? where secretaryPhoneNumber=? and blockName=? and flatNumber=?",[item.flatNumber,secretaryPhoneNumber,id,flatNumber],callback);
    }

};
module.exports=flat;
//flatNumber,blockName,secretaryPhoneNumber