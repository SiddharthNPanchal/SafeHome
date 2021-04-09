var db = require('../dbconnec');
// const { addEveryday } = require('./everyday_model');
var everydayinout = {
    getAllInOutEnteryofSociety: function(secretaryPhoneNumber, callback) {
        return db.query('select e.*,ev.*,ec.*,DATE_FORMAT(e.outtime,"%d %m %Y %T") as outTime,DATE_FORMAT(e.inTime,"%d %m %Y %T") as inTime  from everydayinout e,everyday ev,everydaycategory ec where ec.categoryId=ev.categoryId and ev.everydayPhoneNumber=e.phoneNumber and e.secretaryPhoneNumber=? group by e.InTime', [secretaryPhoneNumber], callback);
    },
    addEverydayInEntries: function(item, callback) {
        return db.query("insert into everydayinout(phoneNumber,InTime,outTime,secretaryPhoneNumber) VALUES (?,NOW(),0,?);", [item.phoneNumber, item.secretaryPhoneNumber], callback);
    },
    addEverydayOutEntries: function(phoneNumber, callback) {
        return db.query("update everydayinout set outTime = NOW() where phoneNumber = ? and outTime=0", [phoneNumber], callback)
    },
    getAllInEntriesofSociety: function(secretaryPhoneNumber, callback) {
        return db.query('select e.*,ev.*,ec.*,DATE_FORMAT(e.outtime,"%d %m %Y %T") as outTime,DATE_FORMAT(e.inTime,"%d %m %Y %T") as inTime from everydayinout e,everyday ev,everydaycategory ec where ec.categoryId=ev.categoryId and ev.everydayPhoneNumber=e.phoneNumber and outTime="0" and e.secretaryPhoneNumber=?', [secretaryPhoneNumber], callback);
    },
    getAllOutEntriesofSociety: function(secretaryPhoneNumber, callback) {
        return db.query('select e.phoneNumber,ev.name,ec.categoryName,ev.categoryId,DATE_FORMAT(e.outtime,"%d %m %Y %T") as outTime,DATE_FORMAT(e.inTime,"%d %m %Y %T") as inTime from everydayinout e,everyday ev,everydaycategory ec where ec.categoryId=ev.categoryId and ev.everydayPhoneNumber=e.phoneNumber and outTime<>"0" and e.secretaryPhoneNumber=?', [secretaryPhoneNumber], callback);
    },
    getAllInEntriesofSocietyforEveryday: function(secretaryPhoneNumber, categoryid, callback) {
        return db.query('select e.phoneNumber,ev.name,ec.categoryName,ev.categoryId,DATE_FORMAT(e.outtime,"%d %m %Y %T") as outTime,DATE_FORMAT(e.inTime,"%d %m %Y %T") as inTime from everydayinout e,everyday ev,everydaycategory ec where ec.categoryId=ev.categoryId and ev.everydayPhoneNumber=e.phoneNumber and outTime="0" and e.secretaryPhoneNumber=? and ev.categoryId=? group by e.phoneNumber', [secretaryPhoneNumber, categoryid], callback);
    },
    getAllInOutEntriesforUser: function(userPhoneNumber, callback) {
        return db.query('select uf.flatNumber,uf.blockName,e.everydayPhoneNumber,e.name,e.image,ec.categoryName,DATE_FORMAT(ee.outtime,"%d %m %Y %T") as outTime,DATE_FORMAT(ee.inTime,"%d %m %Y %T") as inTime from everydayinout ee,userblockeveryday ub,userflat uf,everyday e,everydaycategory ec where e.everydayPhoneNumber=ee.phoneNumber and e.everydayPhoneNumber=ub.everydayPhoneNumber and ec.categoryId=e.categoryId and uf.secretaryPhoneNumber=ub.secretaryPhoneNumber and uf.userPhoneNumber=?', [userPhoneNumber], callback);
    },
    getAllInOutEntriesforUserS: function(secretaryPhoneNumber, userPhoneNumber, blockName, flatNumber, callback) {
        return db.query('select uf.flatNumber,uf.blockName,e.everydayPhoneNumber,e.name,e.image,ec.categoryName,DATE_FORMAT(ee.inTime,"%d %m %Y %T") as inTime,DATE_FORMAT(ee.outTime,"%d %m %Y %T") as outTime from everydayinout ee,userblockeveryday ub,userflat uf,everyday e,everydaycategory ec where e.everydayPhoneNumber=ee.phoneNumber and e.everydayPhoneNumber=ub.everydayPhoneNumber and ec.categoryId=e.categoryId and uf.secretaryPhoneNumber=ub.secretaryPhoneNumber and uf.userPhoneNumber=? and uf.secretaryPhoneNumber=? and uf.blockName=? and uf.flatNumber=? group by inTime order by  outTime  ', [userPhoneNumber, secretaryPhoneNumber, blockName, flatNumber], callback);
    },
    geteverydayname:function(phoneNumber,callback){
        return db.query('select name from everyday where everydayPhoneNumber=?',[phoneNumber],callback);
    },
    giveNotification: function(everydayPhoneNumber, callback) {
        console.log(everydayPhoneNumber);
        return db.query('select u.userToken from userflat uf,users u,everydayinout e,everyday ev,userblockeveryday ub where ub.secretaryPhoneNumber=e.secretaryPhoneNumber and uf.userPhoneNumber=u.userPhoneNumber and uf.secretaryPhoneNumber=e.secretaryPhoneNumber and ev.everydayPhoneNumber=e.phoneNumber and e.phoneNumber=? group by u.userPhoneNumber', [everydayPhoneNumber], callback);
        // return db.query('select e.*,ev.*,ub.*,uf.*,u.* from userflat uf,users u,everydayinout e,everyday ev,userblockeveryday ub where ub.secretaryPhoneNumber=e.secretaryPhoneNumber and uf.userPhoneNumber=u.userPhoneNumber and uf.secretaryPhoneNumber=e.secretaryPhoneNumber and ev.everydayPhoneNumber=e.phoneNumber and e.phoneNumber=? group by u.userPhoneNumber', [everydayPhoneNumber], callback);
    }



};
module.exports = everydayinout;