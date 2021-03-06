package org.telegram.ImplService.startSubMenu;

import dao.DateOfGivingOfIndicatorsDao;
import dao.UserDao;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.DateOfGivingOfIndicators;
import model.User;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.session.Session;
import org.telegram.ImplService.BaseEntityOfCommands;
import org.telegram.http_config.MultiHttpClientConnThread;
import org.telegram.http_config.PoolingHttpClient;
import org.telegram.http_config.PostHttpClient;
import org.telegram.service.Menu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;
import provider.DBIProvider;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.telegram.data.BotMessageText.*;
import static org.telegram.data.MenuButtons.*;


@ToString
@NoArgsConstructor
public class Send_Your_Info extends BaseEntityOfCommands implements Menu {
    public static final String LOGTAG = "Send_Your_Info";
    private UserDao daoOperatorD = DBIProvider.getDao(UserDao.class);
    private DateOfGivingOfIndicatorsDao dateOfGivingOfIndicators = DBIProvider.getDao(DateOfGivingOfIndicatorsDao.class);

    public Send_Your_Info(Update update, Optional<Session> optionalSessio){
        super(update, optionalSessio);
    }
    public SendMessage getSendMessage() {
        BotLogger.info(LOGTAG, "Start method getSendMessage in " +  LOGTAG+ ". ID User = " + id );
        if (optionalSession.get().getAttribute("isAccInBase") == null){
            addAccToBase();
        }//обеъденить обращения к базе!!!!!!
        addCountToBase();

        listCommand.add(MAINMENU);
        listCommand.add(HELP);
        sendMessage.setText(SENTED);
        setButtons(listCommand);
        sendMassegeToBase();
        BotLogger.info(LOGTAG, "End method getSendMessage in " +  LOGTAG + ". ID User = " + id );
        return sendMessage;
    }


    private boolean sendMassegeToBase() {
        CloseableHttpClient client = PoolingHttpClient.getInstance();
        HttpPost post = PostHttpClient.getInstance();
        add_request_parameter (post, optionalSession);

        MultiHttpClientConnThread thread1
                = new MultiHttpClientConnThread(client, post);
        thread1.start();

        BotLogger.info(LOGTAG, "End method sendMassegeToBase in " +  LOGTAG + ". ID User = " + id );
        return true;
    }

    private void add_request_parameter(HttpPost post, Optional<Session> optionalSession){
        BotLogger.info(LOGTAG, "Start method add_request_parameter in " +  LOGTAG + ". ID User = " + id );
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("userAcc", (String) optionalSession.get().getAttribute("YOUR_ACCOUNT")));
        urlParameters.add(new BasicNameValuePair("yourZone", (String) optionalSession.get().getAttribute("YOUR_ZONE")));
        urlParameters.add(new BasicNameValuePair("yourIndicate", (String) optionalSession.get().getAttribute("YOUR_Indicate")));
        BotLogger.info(LOGTAG, "End method add_request_parameter in " +  LOGTAG + ". ID User = " + id );

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            BotLogger.error(LOGTAG, "Error method add_request_parameter in " +  LOGTAG  );
        }
    }

    private void addAccToBase(){
        User user = new User();
        Integer[] arr = new Integer[2];
        arr[0] = Integer.parseInt(optionalSession.get().getAttribute("YOUR_ACCOUNT").toString());
        user.setAcc(arr);
        user.setId_telegram(Integer.parseInt(id));
        daoOperatorD.insert(user);
    }
    private void addCountToBase(){
        DateOfGivingOfIndicators count = new DateOfGivingOfIndicators();
        count.setId_telegram(Integer.parseInt(id));
        dateOfGivingOfIndicators.insert(count);
    }

}
