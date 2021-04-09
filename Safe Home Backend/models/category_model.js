var db=require('../dbconnec');
var category={
    getAllCategory:function(callback){
        return db.query('select * from everydaycategory',callback);
    }

};
module.exports=category;
//everydayPhoneNumber,name,categoryId,secretaryPhoneNumber