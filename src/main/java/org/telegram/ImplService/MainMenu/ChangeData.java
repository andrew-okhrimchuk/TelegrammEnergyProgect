package org.telegram.ImplService.MainMenu;

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
import static org.telegram.data.SubMenuAttribute.INSERT_YOUR_INDICATOR;


@ToString
@NoArgsConstructor
public class ChangeData extends BaseEntity implements Menu {
    public static final String LOGTAG = "ChangeData";

    public ChangeData(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }

    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );

        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(CHANGE_ZONE);
        listCommand.add(CANSEL);
        listCommand.add(HELP);
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
       // optionalSession.get().setAttribute("YOUR_ZONE", null);
        optionalSession.get().setAttribute("YOUR_Indicate", null);
        String titylText = START_TEXT_LEVEL_1_1 + yourAccount +"\n"+ START_TEXT_LEVEL_2_1 + yourZoneAttribute;

            if (yourZoneAttribute.equals(ZONE_1)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_2);
            }
            if (yourZoneAttribute.equals(ZONE_2)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_2_1);
            }
            if (yourZoneAttribute.equals(ZONE_3)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_3);
            }

        setButtons(listCommand);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );

        return sendMessage;
    }
}
