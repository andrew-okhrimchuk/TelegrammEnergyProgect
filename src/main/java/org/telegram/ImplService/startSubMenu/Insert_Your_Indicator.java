package org.telegram.ImplService.startSubMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntity;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.SEND_YOUR_INFO;


@ToString
@NoArgsConstructor
public class Insert_Your_Indicator extends BaseEntity implements Menu {
    public static final String LOGTAG = "Insert_Your_Indicator";

    public Insert_Your_Indicator(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }

    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in Insert_Your_Indicator. ID User = " + id );
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(CHANGE_ZONE);
        listCommand.add(CANSEL);
        listCommand.add(HELP);

        if (!isVerifyYourIndicate(text, yourZoneAttribute)){
            setButtons(listCommand);
            sendMessage.setText(TYPE_INPUT_ERROR_MASSGE_3_1 + TYPE_INPUT_ERROR_MASSGE_3_2_1);
            return sendMessage;
        }

        optionalSession.get().setAttribute("SubMenuAttribute", SEND_YOUR_INFO);
        optionalSession.get().setAttribute("YOUR_Indicate", text);
        yourIndicate = text;
        listCommand.add(SEND);
        listCommand.add(CHANGE_DATA);
        setButtons(listCommand);
        sendMessage.setText(START_TEXT_LEVEL_3_1 + LocalDate.now() + START_TEXT_LEVEL_3_3 + yourAccount + START_TEXT_LEVEL_3_4 + yourZoneAttribute + START_TEXT_LEVEL_3_5 + yourIndicate + START_TEXT_LEVEL_3_6);
        BotLogger.info(LOGTAG, "End method getSendMessage in Insert_Your_Indicator. ID User = " + id );
        return sendMessage;
    }

    private boolean isVerifyYourIndicate(String text, String yourZoneAttribute){
        BotLogger.info(LOGTAG, "Start method verifyIndicate in Insert_Your_Indicator. Your Zone = " + yourZoneAttribute + ", text = " + text);

        if (yourZoneAttribute.equals(ZONE_1)) {
            return Pattern.matches("\\d{1,7}",text);
        }
        if (yourZoneAttribute.equals(ZONE_2)) {
            return Pattern.matches("\\d{1,7}\\s{1}\\d{1,7}",text);
        }
        if (yourZoneAttribute.equals(ZONE_3)) {
            return Pattern.matches("\\d{1,7}\\s{1}\\d{1,7}\\s{1}\\d{1,7}",text);
        }

       //сообщалка об ошибке
        BotLogger.info(LOGTAG, "End method verifyIndicate in Insert_Your_Indicator. Returt false");
        return false;

    }


}
