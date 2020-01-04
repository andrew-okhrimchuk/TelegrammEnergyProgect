package org.telegram.implService.MainMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.implService.BaseEntityOfCommands;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.Optional;

import static org.telegram.data.BotMessageText.ERROR_MASSGE_INFO;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.MenuButtons.HELP;
import static org.telegram.data.SubMenuAttribute.ISEMPTY;


@ToString
@NoArgsConstructor
public class ErrorMassege extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "Cansel";

    public ErrorMassege(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(START);
        listCommand.add(CHAT);
        listCommand.add(HELP);
        setButtons(listCommand);
        sendMessage.setText(ERROR_MASSGE_INFO);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
       return sendMessage;
    }
}
