var db = require('../dbconnec');
var everyday = {
    getAllEveryday: function(id, secretaryPhoneNumber, callback) {
        return db.query('select * from everyday where categoryId=? and secretaryPhoneNumber=?', [id, secretaryPhoneNumber], callback);
    },
    getAllEverydayBySecretaryPhoneNumber: function(secretaryPhoneNumber, callback) {
        return db.query('select * from everyday where secretaryPhoneNumber=?', [secretaryPhoneNumber], callback);
    },
    addEveryday: function(item, callback) {
        return db.query("insert into everyday(everydayPhoneNumber,name,categoryId,secretaryPhoneNumber) values(?,?,?,?)", [item.everydayPhoneNumber, item.name, item.categoryId, item.secretaryPhoneNumber], callback);
    },


    // updateEveryday_image: function(item, filename, callback) {
    //     console.log(item);
    //     return db.query("update everyday set name=?,categoryId=?,secretaryPhoneNumber=?,image=? where everydayPhoneNumber=?", [item.name, item.categoryId, item.secretaryPhoneNumber, filename, id], callback)

    // },
    // addEveryday: function(item, filename, callback) {
    //     return db.query("insert into everyday(everydayPhoneNumber,name,categoryId,secretaryPhoneNumber,image) values(?,?,?,?,?)", [item.everydayPhoneNumber, item.name, item.categoryId, item.secretaryPhoneNumber, filename], callback);
    // },

    deleteEveryday: function(id, callback) {
        return db.query("delete from everyday where everydayPhoneNumber=?", [id], callback);
    },
    getEverydayById: function(id, callback) {
        return db.query("select * from everyday where everydayPhoneNumber=?", [id], callback);
    },
    updateEverydayById: function(id, item, callback) {
        return db.query("update everyday set name=?,categoryId=?,secretaryPhoneNumber=? where everydayPhoneNumber=?", [item.name, item.categoryId, item.secretaryPhoneNumber, id], callback);
    }

};
module.exports = everyday;
//everydayPhoneNumber,name,categoryId,secretaryPhoneNumber