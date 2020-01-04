package org.telegram.ImplService.startSubMenu;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntityOfCommands;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.BotMessageText.START_TEXT_LEVEL_1_2;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.*;


@ToString
@NoArgsConstructor
public class Insert_Your_Acc extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "Insert_Your_Acc";

    public Insert_Your_Acc(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }

    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );

        listCommand.add(CANSEL);
        listCommand.add(HELP);

        //сообщалка об ошибке
        if (!verifyAccount(text)){
            sendMessage.setText(ACC_INPUT_ERROR_MASSGE_1_1);
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_ACCOUNT);
            optionalSession.get().setAttribute("YOUR_ACCOUNT", null);
            optionalSession.get().setAttribute("YOUR_ZONE", null);
            optionalSession.get().setAttribute("YOUR_Indicate", null);
            setButtons(listCommand);
            BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
            return sendMessage;
        }

        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_TYPE);
        optionalSession.get().setAttribute("YOUR_ACCOUNT", text);
        sendMessage.setText(START_TEXT_LEVEL_1_1 + text + START_TEXT_LEVEL_1_2);
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(ZONE_1);
        listCommand.add(ZONE_2);
        listCommand.add(ZONE_3);
        setButtons(listCommand);
        BotLogger.info(LOGTAG, "End method getSendMessage in Insert_Your_Acc. ID User = " + id );
        return sendMessage;
    }

    private boolean verifyAccount(String text){
        BotLogger.info(LOGTAG, "Start method verifyAccount. ID User = " + id );
        return Pattern.matches("[2,4]{1}\\d{8}",text);
    }
}
