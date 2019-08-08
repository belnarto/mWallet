package com.vironit.mwallet.controller.websocket;

import com.vironit.mwallet.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalTime;

/**
 * a message with destination /app/chat.sendMessage will be routed to the sendMessage()
 * method, and a message with destination /app/chat.addUser will be routed to the addUser()
 * method.
 */
@Controller
public class NotificationController {

    @MessageMapping("/bot.sendMessage")
    @SendToUser("/queue/bot")
    public Message sendMessage(@Payload Message message,
                               Principal principal) {
        return new Message(LocalTime.now().toString(),
                "Bot not supported yet",
                "mWalletBot",
                principal.getName());
    }

}
