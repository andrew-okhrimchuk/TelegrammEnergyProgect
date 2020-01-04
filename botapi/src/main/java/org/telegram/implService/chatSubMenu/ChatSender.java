package org.telegram.implService.chatSubMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.implService.BaseEntityOfCommands;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.Optional;

import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.MenuButtons.MAINMENU;
import static org.telegram.data.SubMenuAttribute.ISCHAT;


@ToString
@NoArgsConstructor
public class ChatSender extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "ChatSender";

    public ChatSender(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", ISCHAT);
        listCommand.add(MAINMENU);
        setButtons(listCommand);
        sendMessage.setText(CHAT_ANY_THING);
        sendSomeMassege();
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }

    private boolean sendSomeMassege(){

        return false;
    }
}
