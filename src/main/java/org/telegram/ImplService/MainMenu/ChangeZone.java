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

import static org.telegram.data.BotMessageText.START_TEXT_LEVEL_1_1;
import static org.telegram.data.BotMessageText.START_TEXT_LEVEL_1_2;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.MenuButtons.HELP;
import static org.telegram.data.SubMenuAttribute.INSERT_YOUR_TYPE;


@ToString
@NoArgsConstructor
public class ChangeZone extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "ChangeZone";

    public ChangeZone(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(ZONE_1);
        listCommand.add(ZONE_2);
        listCommand.add(ZONE_3);
        listCommand.add(CANSEL);
        listCommand.add(HELP);
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_TYPE);
        optionalSession.get().setAttribute("YOUR_Indicate", null);
        setButtons(listCommand);
        sendMessage.setText(START_TEXT_LEVEL_1_1 + yourAccount + START_TEXT_LEVEL_1_2);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }
}
