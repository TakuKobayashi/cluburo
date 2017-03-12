var express = require('express');
var app = express();
var ejs=require('ejs');

var port = process.env.PORT || 3000;

//サーバーの立ち上げ
var http = require('http');

//指定したポートにきたリクエストを受け取れるようにする
var server = http.createServer(app).listen(port, function () {
  console.log('Server listening at port %d', port);
});

var io = require('socket.io').listen(server);

var WebSocketServer = require('ws').Server;
var wss = new WebSocketServer({server:server});

app.engine('ejs',ejs.renderFile);

var kintone = require('kintone');

var socket_action = require('./socket_action.js');
 
var soundApi = new kintone('g94co.cybozu.com', { token: "tCuyCZ2VqM8Dl6SfrJuEe08hWBzO6rQRQTbesgwC" });

var soundList = []
soundApi.records.get({app: 4}, function(err, response) {
  soundList = response.records;
});

var connections = [];
wss.on('connection', function (ws) {
  ws.send(
    JSON.stringify({
      action: "play_sound",
      filename: soundList[0].filename.value,
      filepath: soundList[0].filepath.value,
    })
  );

  console.log('connect!!');
  connections.push(ws);
  ws.on('close', function () {
    console.log('close');
    connections = connections.filter(function (conn, i) {
      return (conn === ws) ? false : true;
    });
  });
  ws.on('message', function (message) {
    try {
      var json = JSON.parse(message);
      if(json.action == "change_sound"){
        soundList.forEach(function(s){
          if(s['$id'].value == json.sound_id){
            connections.forEach(function (con, i) {
              con.send(JSON.stringify({
                action: "play_sound",
                filename: s.filename.value,
                filepath: s.filepath.value
              }));
            });
            return;
          }
        });
      }
    } catch (e) {
      console.log("parseError:" + e);
    }
    console.log('message:', message);
    connections.forEach(function (con, i) {
      con.send(message);
    });
  });
});

app.get('/jquery/jquery.js', function(req, res) {
  res.sendFile(__dirname + '/node_modules/jquery/dist/jquery.js');
});
app.get('/', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

app.get('/api/rooms', function(req, res){
  var roomApi = new kintone('g94co.cybozu.com', { token: "8hMuswxuso9IaBYpCrNmsqeV9D8iaGa2jMeHoNlz" });
  roomApi.records.get({app: 7}, function(err, response) {
    var records = response.records
    res.send(JSON.stringify(records.map(function(r){
      return {
        id: parseInt(r['$id'].value),
        name: r.name.value,
        count: parseInt(r.count.value),
        degree: parseInt(r.degree.value),
        description: r.description.value
      };
    })));
  });
});

//ページの表示
app.get('/dj/',function (req,res) {
    console.log("root connected");
    res.render('DJ.ejs');
});

//ページの表示
app.get('/inRoom/',function (req,res) {
    console.log("root connected");
    res.sendFile(__dirname+'/inRoom.html');
});

app.get("/submit",function (req,res) {
    var room_name=req.query['room_name'];
    var room_desc=req.query['room_description'];
    console.log(room_name);

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


//サーバーと接続されると呼ばれる
io.on('connection', function(socket){
  console.log('a user connected');
  //接続している、人達(socket)がサーバーにメッセーッジを送った時にcallbackされるイベントを登録
  //第一引数はイベント名
  socket.on('message', function(msg){
    //受け取った人以外でつながっている人全員に送る場合(broadcastを使う)
    //socket.broadcast.emit('message', 'hello');
    //受け取った人含めて全員に送る場合
    //位第一引数のイベント名に対して送る
    //socket.broadcast.emit('message', msg);
    io.emit('message', msg);
    console.log('message: ' + msg);
  });

  //サーバーとの接続が遮断されると呼ばれる
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
});