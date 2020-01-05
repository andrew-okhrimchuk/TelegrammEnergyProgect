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

import static org.telegram.data.BotMessageText.START_TEXT;
import static org.telegram.data.SubMenuAttribute.INSERT_YOUR_ACCOUNT;


@ToString
@NoArgsConstructor
public class ChangeMyAcc extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "ChangeMyAcc";

    public ChangeMyAcc(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        Start start = new Start(update, optionalSession);
        sendMessage = start.getSendMessage();
        BotLogger.info(LOGTAG, "Start method ChangeMyAcc. ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_ACCOUNT);
       // optionalSession.get().setAttribute("YOUR_ACCOUNT", null);
        optionalSession.get().setAttribute("YOUR_ZONE", null);
        optionalSession.get().setAttribute("YOUR_Indicate", null);
        setButtons(listCommand);
        sendMessage.setText(START_TEXT);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }
}
