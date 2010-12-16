//グローバル変数にWebSocketの変数を定義
var ws;

if (!window["WebSocket"]) 
{
	alert("WebSocket非対応");
}
else 
{
	//オンロード時のイベントに，初期化関数を定義
	window.addEventListener("load", onLoad, false);
	
	//ウィンドウを閉じたり画面遷移した時にWebSokcetを切断する
	window.addEventListener("unload", onUnload, false);
}

//初期化処理
function onLoad()
{
	//HTTPSで接続されている場合，WebSocketもセキュアにする
	var protocol = (location.protocol == "https:") ? "wss" : "ws";
	
	//port番号も込みで取得
	var host = location.host;
	
	//URL
	var pathName = location.pathname;
	pathName = pathName.substring(0, pathName.lastIndexOf("/"));
	
	//接続先URLの組み立て
	var url = protocol + "://" + host + pathName + "/ws/";
	
	//WebSocketのインスタンス化
	ws = new WebSocket(url);
	
	//WebSocketのイベントの登録
	ws.addEventListener("open", onOpenWebSocket, false);
	ws.addEventListener("close", onCloseWebSocket, false);
	ws.addEventListener("message", onMessageWebSocket, false);
}


//ウィンドウを閉じたり画面遷移した時にWebSokcetを切断する
function onUnload(){
	ws.close();
}

//WebSocketが接続された時に，「send」ボタンが有効になる
function onOpenWebSocket(){
	$("#send").click(sendMessage);
	$("#connection").html("connected");
}

//WebSocketが切断された時に，「send」ボタンを無効にする
function onCloseWebSocket(){
	$.event.remove($("#send"), "click", sendMessage);
	$("#message").html("disconnected");
}

//メッセージ入力欄が空白でなければメッセージを送信する
function sendMessage(){
	var message = $("#message").val();
	if (message == "") 
	{
		return;
	}
	var name = $("#name").val();
	if (name == null || name == "") name = "Anonymous";
	var json = {
		"command": "post",
		"name": name,
		"message": message
	}
	ws.send(JSON.stringify(json));
	$("message").val("");
	
}

//接続先よりメッセージを受信した時に，空文字でなければ画面に表示する
function onMessageWebSocket(event){
	var json = JSON.parse(event.data);
	
	var message = json.message;
	if (message == "") 
	{
		return;
	}
	
	var name = json.name;
	if (name == null) name = "Anonymous";
	var date = new Date();
	
	dispMessage("[" + json.date + "] " + name + " > " + message);
}


//画面にメッセージを表示する
//上に表示されるメッセージが最新となる
function dispMessage(msg){
	var elem = $('<div></div>');
	elem.html(msg).addClass("list").css({
		"opacity": 0.0
	}).hide();
	$("#messages").prepend(elem);
	
	var height = elem.height();
	elem.css({
		"height": 0
	}).show().animate({
		"height": height,
		"opacity": 1.0
	}, 200);
}
