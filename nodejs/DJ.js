var express=require('express');
var app=express();
var ejs=require('ejs');
var kintone=require("kintone");

var port=process.env.PORT||3000;
app.engine('ejs',ejs.renderFile);

//サーバーの立ち上げ
var http=require('http');
var server=http.createServer(app).listen(port,function () {
    console.log('Server listening at port %d',port);
});

//ページの表示
app.get('/',function (req,res) {
    console.log("root connected");
    res.render('DJ.ejs');
});

//ページの表示
app.get('/inRoom/',function (req,res) {
    console.log("root connected");
    res.sendFile(__dirname+'/inRoom.html');
});

app.get("/submit",function (req,res) {
    var room_name=req.param('room_name');
    var room_desc=req.param('room_description');

    var query={
        "name": {
            "value": room_name
        },
        "description": {
            "value": room_desc
        },"degree": {
            "value": "0"
        },"count": {
            "value": "0"
        }
    };
    var api_room=new kintone("g94co.cybozu.com",{ token: "8hMuswxuso9IaBYpCrNmsqeV9D8iaGa2jMeHoNlz" });
    api_room.record.post({ app: 7,record: query },function (err,result) {
        var api_sound=new kintone("g94co.cybozu.com",{ token: "tCuyCZ2VqM8Dl6SfrJuEe08hWBzO6rQRQTbesgwC" });
        var sounds=[];
        api_sound.records.get({ app: 4 },function (err,response) {
            var arry=response["records"];
            arry.forEach(function (value) {
                var sound={
                    'filename': value["filename"]["value"],
                    'filepath': value["filepath"]["value"]
                };
                if (value["filename"]["value"]!="test1"&&value["filename"]["value"]!="test2")
                    sounds.push(sound);
            });
            if (result["errors"]==null) {
                res.render('inRoom.ejs',{
                    room_name: room_name,
                    room_description: room_desc,
                    sounds: sounds
                });
            } else {
                var err_text;
                if (result["errors"]["record.name.value"]!=null) {
                    err_text="同じ名前のルームがすでに存在しています。"
                } else {
                    err_text=JSON.stringify(result["errors"]);
                }
                res.render('err.ejs',{
                    err: err_text
                });
            }
        });
    });
});
