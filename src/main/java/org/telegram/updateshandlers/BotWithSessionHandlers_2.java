package org.telegram.updateshandlers;

import org.apache.shiro.session.Session;
import org.telegram.data.SubMenuAttribute;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;
import session.TelegramLongPollingSessionBot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.*;

public class BotWithSessionHandlers_2 extends TelegramLongPollingSessionBot {
    public static final String LOGTAG = "BotWithSessionHandlers";

    private Optional<Session> optionalSession;
    private List<String> listCommand;
    private SubMenuAttribute subMenuAttribute;
    private SendMessage sendMessage;
    private String id, text, yourAccount, yourZoneAttribute, yourIndicate, errorMassge;

    @Override
    public void onUpdateReceived(Update update, Optional<Session> optionalSession) {
        init(update, optionalSession);
        BotLogger.info(LOGTAG, "Start method onUpdateReceived(Update update, Optional<Session> optionalSession). ID User = " + id );

        if (text.equals(MAINMENU) || text.equals(FIRST_START)) {
            main_menu();
            return;
        }
        if (text.equals(START)) {
            sendMessage.setText(START_TEXT);
            start();
            return;
        }
        if (text.equals(HELP)) {
            help();
            return;
        }
        if (text.equals(CHAT)) {
            chat();
            return;
        }
        if (text.equals(CANSEL)) {
            cansel();
            return;
        }
        if (text.equals(CHANGE_MY_ACC)) {
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_ACCOUNT);
            optionalSession.get().setAttribute("YOUR_ACCOUNT", null);
            optionalSession.get().setAttribute("YOUR_ZONE", null);
            optionalSession.get().setAttribute("YOUR_Indicate", null);
            sendMessage.setText(START_TEXT);
            start();
            return;
        }
        if (text.equals(CHANGE_ZONE)) {
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_TYPE);
            optionalSession.get().setAttribute("YOUR_ZONE", null);
            optionalSession.get().setAttribute("YOUR_Indicate", null);

            insert_your_type_zone();
            return;
        }
        if (text.equals(CHANGE_DATA)) {
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
            optionalSession.get().setAttribute("YOUR_Indicate", null);
            insert_your_indicator();
            return;
        }

        else subMenu(text);
    }


    private void subMenu(String text){
        BotLogger.info(LOGTAG, "Start method subMenu. ID User = " + id +" with message = " + text );

        if (subMenuAttribute.equals(INSERT_YOUR_ACCOUNT)) {
            if (!verifyAccount(text)){
                //сообщалка об ошибке
                sendMessage.setText(ACC_INPUT_ERROR_MASSGE_1_1);
                start();
                return;
            }
            optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_TYPE);
            optionalSession.get().setAttribute("YOUR_ACCOUNT", text);
            sendMessage.setText(START_TEXT_LEVEL_1_1 + text + START_TEXT_LEVEL_1_2);
            insert_your_type_zone();
            return;
        }
        if (subMenuAttribute.equals(INSERT_YOUR_TYPE)) {
            if(text.equals(ZONE_1) || text.equals(ZONE_2) || text.equals(ZONE_3)) {
                optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
                optionalSession.get().setAttribute("YOUR_ZONE", text);
                yourZoneAttribute = text;
                insert_your_indicator();
                return;
            }
            //сообщалка об ошибке
            sendMessage.setText(START_TEXT_LEVEL_1_1 + yourAccount + START_TEXT_LEVEL_1_2);
            insert_your_type_zone();
            return;
        }
        if (subMenuAttribute.equals(INSERT_YOUR_INDICATOR)) {
            int zone = 1;
            if (yourZoneAttribute.equals(ZONE_2)) {zone = 2;}
            if (yourZoneAttribute.equals(ZONE_3)) {zone = 3;}

            if (!verifyIndicate(text, zone)){
                String type = NAME_ZONE_1;
                if (zone == 2){type = NAME_ZONE_2;}
                if (zone == 3){type = NAME_ZONE_3;}
                //сообщалка об ошибке
                errorMassge = TYPE_INPUT_ERROR_MASSGE_3_1 +  TYPE_INPUT_ERROR_MASSGE_3_2_1;
                insert_your_indicator();
                errorMassge = null;
                return;
            }
            optionalSession.get().setAttribute("SubMenuAttribute", SEND_YOUR_INFO);
            optionalSession.get().setAttribute("YOUR_Indicate", text);
            yourIndicate = text;
            send_your_info();
            return;
        }

        if (subMenuAttribute.equals(SEND_YOUR_INFO)) {
            optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
            past_send_your_info();
            return;
        }
        return;
    }
    private void main_menu(){
        BotLogger.info(LOGTAG, "Start method main_menu. ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(START);
        listCommand.add(CHAT);
        listCommand.add(HELP);

        sendMessage.setText(FIRST_START_TEXT);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method main_menu. ID User = " + id );

    }
    private void start(){
        BotLogger.info(LOGTAG, "Start method start. ID User = " + id );
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_ACCOUNT);
        listCommand.add(CANSEL);
        listCommand.add(HELP);
        optionalSession.get().setAttribute("YOUR_ACCOUNT", null);
        optionalSession.get().setAttribute("YOUR_ZONE", null);
        optionalSession.get().setAttribute("YOUR_Indicate", null);

        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method start. ID User = " + id );

    }
    private void help(){
        this.optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(MAINMENU);
        sendMessage.setText(HELP_TEXT);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        BotLogger.info(LOGTAG, "End method help. ID User = " + id );

    }
    private void chat(){
        this.optionalSession.get().setAttribute("SubMenuAttribute", ISCHAT);
        listCommand.add(MAINMENU);

        sendMessage.setText(HELP_TEXT);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        BotLogger.info(LOGTAG, "End method chat. ID User = " + id );
    }
    private void cansel(){
        this.optionalSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
        listCommand.add(MAINMENU);
        sendMessage.setText(HELP_TEXT);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        BotLogger.info(LOGTAG, "End method chat. ID User = " + id );
    }

    private void insert_your_type_zone(){
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(ZONE_1);
        listCommand.add(ZONE_2);
        listCommand.add(ZONE_3);
        listCommand.add(CANSEL);
        listCommand.add(HELP);

        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method insert_your_type_zone. ID User = " + id );

    }
    private void insert_your_indicator(){
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(CHANGE_ZONE);
        listCommand.add(CANSEL);
        listCommand.add(HELP);


        String titylText = START_TEXT_LEVEL_1_1 + yourAccount +"\n"+ START_TEXT_LEVEL_2_1 + yourZoneAttribute;
        if (errorMassge != null){
            sendMessage.setText(errorMassge);
        }
        else {
            if (yourZoneAttribute.equals(ZONE_1)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_2);
            }
            if (yourZoneAttribute.equals(ZONE_2)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_2_1);
            }
            if (yourZoneAttribute.equals(ZONE_3)) {
                sendMessage.setText(titylText + START_TEXT_LEVEL_2_3);
            }
        }
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method insert_your_indicator. ID User = " + id );

    }
    private void send_your_info(){

        listCommand.add(SEND);
        listCommand.add(CHANGE_MY_ACC);
        listCommand.add(CHANGE_ZONE);
        listCommand.add(CHANGE_DATA);
        listCommand.add(CANSEL);
        listCommand.add(HELP);


    //    optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
        if (errorMassge != null){
            sendMessage.setText(errorMassge);
        }
        else sendMessage.setText(START_TEXT_LEVEL_3_1 + LocalDate.now() + START_TEXT_LEVEL_3_3 + yourAccount + START_TEXT_LEVEL_3_4 + yourZoneAttribute + START_TEXT_LEVEL_3_5 +yourIndicate + START_TEXT_LEVEL_3_6);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method send_your_info. ID User = " + id );

    }
    private void past_send_your_info(){
        listCommand.add(MAINMENU);
        listCommand.add(HELP);

        //    optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_INDICATOR);
        sendMessage.setText(SENTED);
        try {
            setButtons(sendMessage, listCommand);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, e);
        }
        BotLogger.info(LOGTAG, "End method past_send_your_info. ID User = " + id );


    }

    private boolean verifyAccount(String text){
        BotLogger.info(LOGTAG, "Start method verifyAccount. ID User = " + id );
        return Pattern.matches("[2,4]{1}\\d{8}",text);
    }
    private boolean verifyIndicate(String text, int x){
        BotLogger.info(LOGTAG, "Start method verifyIndicate. your Zone = " + x + " " + text);

        if (x==1) {return Pattern.matches("\\d{1,7}",text);}
        if (x==2) {return Pattern.matches("\\d{1,7}\\s{1}\\d{1,7}",text);}
        if (x==3) {return Pattern.matches("\\d{1,7}\\s{1}\\d{1,7}\\s{1}\\d{1,7}",text);}
        else
            BotLogger.info(LOGTAG, "End method verifyIndicate. your Zone = " + x + "\n Returt false");
        return false;
    }
    private void init(Update update, Optional<Session> optionalSession){
        this.optionalSession = optionalSession;
        text = update.getMessage().getText();
        id = optionalSession.get().getId().toString();
        subMenuAttribute = (SubMenuAttribute) optionalSession.get().getAttribute("SubMenuAttribute");
        yourZoneAttribute = (String) optionalSession.get().getAttribute("YOUR_ZONE");
        yourAccount = (String) optionalSession.get().getAttribute("YOUR_ACCOUNT");
        yourIndicate = (String) optionalSession.get().getAttribute("YOUR_Indicate");
        listCommand = new ArrayList<>();
        sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(id);
    }
    public void setButtons(SendMessage sendMessage, List<String> listNameKeyboardButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        for (int i = 0; i < listNameKeyboardButton.size(); i++) {
            if (i % 2 == 0 && i != 0){
                keyboardRowList.add(keyboardFirstRow);
                keyboardFirstRow = new KeyboardRow();
            }
            keyboardFirstRow.add(new KeyboardButton(listNameKeyboardButton.get(i)));
        }
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }
}
