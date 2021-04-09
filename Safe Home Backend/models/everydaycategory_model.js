var db=require('../dbconnec');
var everydaycategory={
    getAllEverydayCategory:function(callback){
        return db.query('select * from everydaycategory',callback);
    },
    addEverydayCategory:function(item,callback){
        return db.query("insert into everydaycategory(categoryName) values(?)",[item.categoryName],callback);
    },
    deleteEverydayCategory:function(id,callback){
        return db.query("delete from everydaycategory where categoryId=?",[id],callback);
    },
    getEverydayCategoryById:function(id,callback){
        return db.query("select * from everydaycategory where categoryId=?",[id],callback);
    },
    updateEverydayCategoryById:function(id,item,callback){
        return db.query("update everydaycategory set categoryName=? where categoryId=?",[item.categoryName,id],callback);
    }

};
module.exports=everydaycategory;
//categoryId,categoryname