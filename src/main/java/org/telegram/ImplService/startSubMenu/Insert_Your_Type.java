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
import static org.telegram.data.SubMenuAttribute.*;


@ToString
@NoArgsConstructor
public class Insert_Your_Type extends BaseEntity implements Menu {
    private static final String LOGTAG = "Insert_Your_Type";

    public Insert_Your_Type(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }

    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG+ ". ID User = " + id );
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(CANSEL);
        listCommand.add(HELP);

        if(text.equals(ZONE_1) || text.equals(ZONE_2) || text.equals(ZONE_3)) {
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
            optionalSession.get().setAttribute("YOUR_ZONE", text);
            yourZoneAttribute = text;
            listCommand.add(CHANGE_ZONE);
            setTextEqualTypeZone();
            setButtons(listCommand);
            return sendMessage;
        }
        //сообщалка об ошибке
        else {
            sendMessage.setText(START_TEXT_LEVEL_1_1 + yourAccount + TYPE_INPUT_ERROR_MASSGE_2_1 + START_TEXT_LEVEL_1_2);
            listCommand.add(ZONE_1);
            listCommand.add(ZONE_2);
            listCommand.add(ZONE_3);
            setButtons(listCommand);
        }
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );

        return sendMessage;
    }


    private void setTextEqualTypeZone(){
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
        BotLogger.info(LOGTAG, "End method setTextEqualTypeZone in " +  LOGTAG + ". ID User = " + id );

    }

}
