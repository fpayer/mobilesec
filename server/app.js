var databaseURL = "mydb";
var collections = ["gps"];
var db = require("mongojs").connect(databaseURL, collections);

db.gps.save(
