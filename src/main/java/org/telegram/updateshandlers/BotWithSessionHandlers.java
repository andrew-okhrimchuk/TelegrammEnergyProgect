package org.telegram.updateshandlers;

import org.apache.shiro.session.Session;
import org.telegram.ImplService.MainMenu.*;
import org.telegram.ImplService.chatSubMenu.ChatSender;
import org.telegram.ImplService.startSubMenu.*;
import org.telegram.data.SubMenuAttribute;
import org.telegram.service.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;
import session.TelegramLongPollingSessionBot;

import java.util.Optional;

import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.*;

public class BotWithSessionHandlers extends TelegramLongPollingSessionBot {
    public static final String LOGTAG = "BotWithSessionHandlers";


    @Override
    public void onUpdateReceived(Update update, Optional<Session> optionalSession) {
        String text = update.getMessage().getText();
        BotLogger.info(LOGTAG, "Start method onUpdateReceived in " + LOGTAG +". ID User = " + optionalSession.get().getId() );

        if (text.equals(MAINMENU) || text.equals(FIRST_START)) {
            Menu menu = new MainMenu(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(START)) {
            Menu menu = new Start(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(HELP)) {
            Menu menu = new Help(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(CHAT)) {
            Menu menu = new Chat(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(CANSEL)) {
            Menu menu = new Cansel(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(CHANGE_MY_ACC)) {
            Menu menu = new ChangeMyAcc(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(CHANGE_ZONE)) {
            Menu menu = new ChangeZone(update, optionalSession);
            send_massege (menu);
            return;
        }
        if (text.equals(CHANGE_DATA)) {
            Menu menu = new ChangeData(update, optionalSession);
            send_massege (menu);
            return;
        }

        if (startSubMenu(update, optionalSession)){
            return;
        };
        if (chatSubMenu(update, optionalSession)){
            return;
        }
        else {
            Menu menu = new ErrorMassege(update, optionalSession);
            send_massege (menu);
            return;
        }

    }


    private boolean startSubMenu(Update update, Optional<Session> optionalSession){
        BotLogger.info(LOGTAG, "Start method startSubMenu, with message = " + update.getMessage().getText() );
        SubMenuAttribute subMenuAttribute = (SubMenuAttribute) optionalSession.get().getAttribute("SubMenuAttribute");

        if (subMenuAttribute.equals(INSERT_YOUR_ACCOUNT)) {
            Menu menu = new Insert_Your_Acc(update, optionalSession);
            send_massege (menu);
            return true;
        }
        if (subMenuAttribute.equals(INSERT_YOUR_TYPE)) {
            Menu menu = new Insert_Your_Type(update, optionalSession);
            send_massege (menu);
            return true;
        }
        if (subMenuAttribute.equals(INSERT_YOUR_INDICATOR)) {
            Menu menu = new Insert_Your_Indicator(update, optionalSession);
            send_massege (menu);
            return true;
        }

        if (subMenuAttribute.equals(SEND_YOUR_INFO)) {
            Menu menu = new Send_Your_Info(update, optionalSession);
            send_massege (menu);
            return true;
        }
        return false;
    }

    private boolean chatSubMenu(Update update, Optional<Session> optionalSession){
        BotLogger.info(LOGTAG, "Start method chatSubMenu, with message = " + update.getMessage().getText() );
        SubMenuAttribute subMenuAttribute = (SubMenuAttribute) optionalSession.get().getAttribute("SubMenuAttribute");

        if (subMenuAttribute.equals(ISCHAT)) {
            Menu menu = new ChatSender(update, optionalSession);
            send_massege (menu);
            return true;
        }

        return false;
    }

    private void send_massege (Menu menu){
        BotLogger.info(LOGTAG, "Start method send_massege in class " + LOGTAG );

        SendMessage sendMessage = menu.getSendMessage();

        try {
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method send_massege in class " + LOGTAG +". ID User = " + sendMessage.getChatId() );
    }
}
