'use strict';

var chatPage = document.querySelector('#chat-page');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

var socket;

function connect() {
    username = "${pageContext.request.userPrincipal.name}";
    if (username) {
        chatPage.classList.remove('hidden');

        socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected);
    }
}

function onConnected() {
    // Subscribe to the notification channel
    stompClient.subscribe('/user/queue/notify', onMessageReceived);
    // Subscribe to the bot communication channel
    stompClient.subscribe('/user/queue/bot', onMessageReceived);
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            time: new Date().toISOString().split("T")[1].replace("Z", ""),
            content: messageInput.value,
            sender: username,
            receiver: "mWalletBot"
        };
        stompClient.send("/app/bot.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    var timeElement = document.createElement('p');
    var timeText = document.createTextNode(message.time);
    timeElement.appendChild(timeText);

    messageElement.appendChild(timeElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender + ':');
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

connect();
messageForm.addEventListener('submit', sendMessage, true)