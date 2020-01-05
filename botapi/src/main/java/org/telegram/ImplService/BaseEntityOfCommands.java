package org.telegram.ImplService;

import dao.UserDao;
import lombok.*;
import org.apache.shiro.session.Session;
import org.telegram.data.SubMenuAttribute;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import provider.DBIProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ToString
@Getter
@Setter
@NoArgsConstructor
public class BaseEntityOfCommands {
    private UserDao daoOperatorD = DBIProvider.getDao(UserDao.class);
    protected List<String> listCommand;
    protected SubMenuAttribute subMenuAttribute;
    protected SendMessage sendMessage;
    protected String id;
    protected String text;
    protected String yourZoneAttribute;
    protected String yourIndicate;
    protected String errorMassge;
    protected String yourAccount;
    protected Optional<Session> optionalSession;
    protected Update update;


    public BaseEntityOfCommands(Update update, Optional<Session> optionalSession) {
        this.optionalSession = optionalSession;
        this.update = update;
        this.id = optionalSession.get().getId().toString();
        this.subMenuAttribute = (SubMenuAttribute) optionalSession.get().getAttribute("SubMenuAttribute");
        this.yourZoneAttribute = (String) optionalSession.get().getAttribute("YOUR_ZONE");
        this.yourAccount = (String) optionalSession.get().getAttribute("YOUR_ACCOUNT");
        this.yourIndicate = (String) optionalSession.get().getAttribute("YOUR_Indicate");
        this.text = update.getMessage().getText();
        this.listCommand = new ArrayList<>();
        this.sendMessage = new SendMessage();
        this.sendMessage.enableMarkdown(true);
        this.sendMessage.setChatId(id);
    }
    public void setButtons(List<String> listNameKeyboardButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.sendMessage.setReplyMarkup(replyKeyboardMarkup);
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
