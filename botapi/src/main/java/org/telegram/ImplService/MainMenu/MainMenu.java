package org.telegram.ImplService.MainMenu;

import lombok.*;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntityOfCommands;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.Optional;

import static org.telegram.data.BotMessageText.FIRST_START_TEXT;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.ISEMPTY;


@ToString
@NoArgsConstructor
public class MainMenu extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "MainMenu";

    public MainMenu(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in MainMenu. ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(START);
        listCommand.add(CHAT);
        listCommand.add(HELP);
        setButtons(listCommand);
        sendMessage.setText(FIRST_START_TEXT);
        BotLogger.info(LOGTAG, "End method getSendMessage in MainMenu. ID User = " + id );
        return sendMessage;
    }
}
