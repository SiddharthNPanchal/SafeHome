var db = require('../dbconnec');
const { response } = require('../app');
var notice = {
    getNotice: function(secretaryPhoneNumber, callback) {
        return db.query("select id,title,secretaryPhoneNumber,message,DATE_FORMAT(date, '%d %M %Y')as date  from notice where secretaryPhoneNumber=? ORDER BY id DESC ", [secretaryPhoneNumber], callback);
    },
    insertNotice: function(item, callback) {
        //console.log(item);
        return db.query("insert into notice values(?,?,?,?,now())", [item.id, item.secretaryPhoneNumber, item.title, item.message], callback);
    },
    updateNotice: function(id, item, callback) {
        //  console.log(item);
        return db.query("update  notice set secretaryPhoneNumber=? , message=? ,date=?,title=?  where id=?", [item.secretaryPhoneNumber, item.message, item.date, item.title, id], callback);
    },
    deleteNotice: function(id, callback) {
        //  console.log(item);
        return db.query("delete from notice  where id=?", [id], callback);
    },
    getLastNotice: function(secretaryPhoneNumber, callback) {
        return db.query("select id,title,secretaryPhoneNumber,message,DATE_FORMAT(date, '%d %M %Y')as date  from notice where secretaryPhoneNumber=? ORDER BY id DESC limit 1", [secretaryPhoneNumber], callback);
    }

};
module.exports = notice;