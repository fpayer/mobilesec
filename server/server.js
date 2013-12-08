var http = require("http");
var express = require("express");
var fs = require("fs");
var path = require("path");

var databaseURL = "mydb";
var collections = ["gps", "log"];
var db = require("mongojs").connect(databaseURL, collections);

var app = express();
app.use(express.bodyParser());

http.createServer(app).listen(80);

app.get("/", function(req, res){
    res.send("");
});

app.get("/restart", function(req, res){
    db.log.remove();
    db.gps.remove();
    res.send("Database successfully cleared");
});

app.get("/logdata", function(req, res){
    vals = [];
    db.log.find(function(err, logs){
        if (err || !logs) res.send("No logs");
        else logs.forEach(function(line) {
            vals.push({"event" : line.event, "level" : line.level, "date" : line.date});
        });
        res.send(200, JSON.stringify(vals));
    });
});

app.get("/log", function(req, res){
    fs.readFile("./log.html", function(error, content){
        if(error) {
            res.writeHead(500);
            res.end();
        } else {
            res.writeHead(200, {"Content-Type" : "text/html"});
            res.end(content, "utf-8");
        }
    });
});

app.post("/status", function(req, res){
    var json = JSON.parse(req.body.json);
    var date = new Date();
    db.log.save({"Device": "Test1", "event" : json.event, "level" : json.level, "date" : date.toString()});
    res.send("Ok");
});

app.get("/cords", function(req, res){
   vals = [];
   db.gps.find(function(err, coords){
       if (err || !coords) res.send("No GPS coordinates");
       else coords.forEach( function(coord) {
            //entry = "Lat: " + coord.lat + " Lon: " + coord.lon + "</br>";
            vals.push({"lat" : coord.lat, "lng" : coord.lon, "date" : coord.date});
       });
       vals = vals.reverse().slice(0, 100);
       res.send(200, JSON.stringify(vals));
   }); 
});

app.get("/gps", function(req, res){
   fs.readFile("./map.html", function(error, content) {
       if (error) {
            res.writeHead(500);
            res.end();
       } else {
            res.writeHead(200, { 'Content-Type': "text/html"});
            res.end(content, 'utf-8');
       }
   });
});

app.post("/gps", function(req, res){
    var json = req.body.json;
    json = JSON.parse(json);
    lat = json.lat;
    lon = json.lon;
    console.log(lat + " " + lon);
    var date = new Date();
    db.gps.save({"Device":"Test1", "lat" : lat, "lon" : lon, "date" : date.toString()})
    res.send("Ok");
});

app.post("/img", function(req, res){
    console.log(req);
    console.log(req.files);
});

app.post("/", function(req, res){
    res.send("Ok");
});

app.get("/poll", function(req, res){
    res.send(stat);
});


app.get("/admin*", function(req, res){
    console.log(req.url);
    var base = "./admin/";
    var branch = "";
    var contentType = "text/html";
    if(req.url == "/admin" || req.url == "/admin/")
        branch = "admin.html";
    else
        branch = req.url.substring(7);

    var extname = path.extname(branch);
    switch(extname) {
        case '.js' : 
            contentType = "text/javascript";
            break;
        case ".css" :
            contentType = "text/css";
            break;
        case ".jpg" : 
            contentType = "image/jpeg";
            break;
        case ".png" :
            contentType = "image/png";
            break;
    }

    var filePath = base+branch;
    if (path.exists(filePath, function(exists) {
        if (exists) {
            fs.readFile(filePath, function(error, content) {
                if (error) {
                    res.writeHead(500);
                    res.end();
                } else {
                    res.writeHead(200, { 'Content-Type': contentType});
                    res.end(content, 'utf-8');
                }
            });
        }
        else {
            res.writeHead(500);
            res.end();
        }
    }));
});
