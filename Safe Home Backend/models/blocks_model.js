var db=require('../dbconnec');
var flat={
    getAllBlock:function(callback){
        return db.query('select * from block',callback);
    },
    addBlock:function(item,callback){
        return db.query("insert into block(blockName,secretaryPhoneNumber) values(?,?)",[item.blockName,item.secretaryPhoneNumber],callback);
    },
    deleteBlock:function(blockName,secretaryPhoneNumber,callback){
        return db.query("delete from block where blockName=? and secretaryPhoneNumber=?",[blockName,secretaryPhoneNumber],callback);
    },
    getBlockById:function(blockName,secretaryPhoneNumber,callback){
        return db.query("select * from block where blockName=? and secretaryPhoneNumber=?",[blockName,secretaryPhoneNumber],callback);
    },
    updateBlockById:function(blockName,secretaryPhoneNumber,item,callback){
        return db.query("update block set blockName=? where secretaryPhoneNumber=? and blockName=?",[item.blockName,secretaryPhoneNumber,blockName],callback);
    },
    getBlockBySecretaryPhoneNumber:function(secretaryPhoneNumber,callback){
        return db.query('select * from block where secretaryPhoneNumber=?',[secretaryPhoneNumber],callback);
    }

};
module.exports=flat;
//flatName,secretaryPhoneNumber,numberOfFloor