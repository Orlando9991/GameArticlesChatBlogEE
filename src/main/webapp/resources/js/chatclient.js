/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


var clientSocket = new WebSocket("ws://localhost:8080/GameBlog/chat");
var clientUsername;
var chatMessageContainer;
    
clientSocket.onopen = function (event) {
    console.log('open' + event);
};

clientSocket.onmessage = function (event) {
    //get message
    var message = event.data;

    //create dynamically new html element
    var chatContainer = document.getElementById("chat-message-container");
    var newMessageElement = createNewMsgElement(message);
    chatContainer.appendChild(newMessageElement);
    scrollChatToBottom ();
};

clientSocket.onclose = function (event) {
    setDisableSendButton(true);
    clientUsername = null;
};

clientSocket.onerror = function (event) {
    setDisableSendButton(true);
    clientUsername = null;
    console.log(event.data);
};

function createNewMsgElement(message) {
    var newElement = document.createElement("div");
    var cssClass = document.createAttribute("class");
    
    var userName = message.toString().split(':')[0];
    cssClass.value = "text2xl margin-low padding-low radius-low border-shadow textwrap";
    
    if(clientUsername === userName){
       cssClass.value = cssClass.value.concat(" background-blue white-text");
    }else{
       cssClass.value = cssClass.value.concat(" background-white border-gray");
    }
    
    newElement.setAttributeNode(cssClass);
    var textItem = document.createTextNode(message);
    newElement.appendChild(textItem);
    return newElement;
}

function sendSocketMessage(userName) {
    if(clientUsername == null){
        clientUsername = userName;
    }
    var message = document.getElementById("chat-input").value;
    clientSocket.send(clientUsername.toString().concat(": ").concat(message));
}


function scrollChatToBottom () {
    if(chatMessageContainer == null){
        chatMessageContainer = document.getElementById("chat-message-container");
    }
    chatMessageContainer.scrollTop = chatMessageContainer.scrollHeight;
}




