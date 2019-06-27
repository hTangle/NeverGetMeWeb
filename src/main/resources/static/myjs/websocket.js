// var wsUri = "ws://localhost:8088/websocket";
var wsUri = "wss://www.nevergetme.com:8088/websocket";

function debug(message) {
    console.log(message);
}

function sendMessage(websocket,msg) {
    if (websocket != null) {
        websocket.send(msg);
        console.log("string sent :", '"' + msg + '"');
    } else {
        //initWebSocket();
        websocket.send(msg);
        console.log("string sent :", '"' + msg + '"');
    }
    // stopWebSocket();
}


function initWebSocket() {
    let websocket;
    try {
        if (typeof MozWebSocket == 'function')
            WebSocket = MozWebSocket;
        if (websocket && websocket.readyState == 1)
            websocket.close();
        websocket = new WebSocket(wsUri);
        websocket.onopen = function (evt) {
            // sendMessage("1");
            debug("CONNECTED");
        };
        websocket.onclose = function (evt) {
            debug("DISCONNECTED");
        };
        websocket.onmessage = function (evt) {
            console.log("Message received :", evt);
            changeMessageIcon();
            //$("#HeaderMessageButton").text('消息'+addMessageNotify());
        };
        websocket.onerror = function (evt) {
            debug('ERROR: ' + evt.data);
        };
    } catch (exception) {
        debug('ERROR: ' + exception);
    }
    return websocket;
}

function stopWebSocket(websocket) {
    if (websocket)
        websocket.close();
}

function checkSocket(websocket) {
    if (websocket != null) {
        var stateStr;
        switch (websocket.readyState) {
            case 0: {
                stateStr = "CONNECTING";
                break;
            }
            case 1: {
                stateStr = "OPEN";
                break;
            }
            case 2: {
                stateStr = "CLOSING";
                break;
            }
            case 3: {
                stateStr = "CLOSED";
                break;
            }
            default: {
                stateStr = "UNKNOW";
                break;
            }
        }
        debug("WebSocket state = " + websocket.readyState + " ( " + stateStr + " )");
    } else {
        debug("WebSocket is null");
    }
}