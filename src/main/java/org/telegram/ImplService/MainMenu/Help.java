package org.telegram.ImplService.MainMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntityOfCommands;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.Optional;

import static org.telegram.data.BotMessageText.HELP_TEXT;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.ISEMPTY;


@ToString
@NoArgsConstructor
public class Help extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "Help";

    public Help(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }

    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(MAINMENU);
        setButtons(listCommand);
        sendMessage.setText(HELP_TEXT);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }
}
