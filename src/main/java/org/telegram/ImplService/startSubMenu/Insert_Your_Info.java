package org.telegram.ImplService.startSubMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntity;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;
import java.util.Optional;
import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.MenuButtons.*;


@ToString
@NoArgsConstructor
public class Insert_Your_Info extends BaseEntity implements Menu {
    public static final String LOGTAG = "Insert_Your_Info";

    public Insert_Your_Info(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG+ ". ID User = " + id );
        listCommand.add(MAINMENU);
        listCommand.add(HELP);
        sendMessage.setText(SENTED);
        setButtons(listCommand);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }




}
