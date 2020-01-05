package org.telegram.ImplService.MainMenu;

import dao.UserDao;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.User;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntityOfCommands;
import org.telegram.ImplService.startSubMenu.Insert_Your_Acc;
import org.telegram.ImplService.startSubMenu.Insert_Your_Indicator;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;
import provider.DBIProvider;

import java.util.Optional;

import static org.telegram.data.BotMessageText.START_TEXT;
import static org.telegram.data.MenuButtons.*;
import static org.telegram.data.SubMenuAttribute.INSERT_YOUR_ACCOUNT;
import static org.telegram.data.SubMenuAttribute.INSERT_YOUR_TYPE;


@ToString
@NoArgsConstructor
public class Start extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "Start";
    private UserDao daoOperatorD = DBIProvider.getDao(UserDao.class);

    public Start(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        if(isAccNull() && isAccInBase()) {
            getAccInBasePsql();
        }
        if(!isAccNull()) {
            return getSendMessag();
        }

       // optionalSession.get().setAttribute("YOUR_ACCOUNT", null);
        listCommand.add(CANSEL);
        listCommand.add(HELP);
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_ACCOUNT);
        optionalSession.get().setAttribute("YOUR_ZONE", null);
        optionalSession.get().setAttribute("YOUR_Indicate", null);
        setButtons(listCommand);
        sendMessage.setText(START_TEXT);
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }

    private  boolean isAccNull(){
        return optionalSession.get().getAttribute("YOUR_ACCOUNT")==null;
    }
    private  boolean isAccInBase(){
        return optionalSession.get().getAttribute("isAccInBase")==null;
    }
    private void getAccInBasePsql(){
        BotLogger.info(LOGTAG, "Start method getAccInBasePsql in " +  LOGTAG + ". ID User = " + id );

        User user = daoOperatorD.getWithUser(Integer.parseInt(id));
        if (!(user==null) && !(user.getAcc()==null)) {
            String acc = (user.getAcc()[0]).toString();
            optionalSession.get().setAttribute("isAccInBase", "Yes");
            optionalSession.get().setAttribute("YOUR_ACCOUNT", acc);
        }
    }
    private SendMessage getSendMessag(){
        Insert_Your_Acc insert_your_acc = new Insert_Your_Acc(update, optionalSession);
        insert_your_acc.setText((String) optionalSession.get().getAttribute("YOUR_ACCOUNT"));
        SendMessage messege = insert_your_acc.getSendMessage();
        optionalSession.get().setAttribute("SubMenuAttribute", INSERT_YOUR_TYPE);
        return messege;
    }


}
