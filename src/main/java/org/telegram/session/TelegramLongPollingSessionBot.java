package org.telegram.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.telegram.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.telegram.data.SubMenuAttribute.ISEMPTY;

@SuppressWarnings({"WeakerAccess", "OptionalUsedAsFieldOrParameterType", "unused"})
public abstract class TelegramLongPollingSessionBot extends TelegramLongPollingBot  {

    DefaultSessionManager sessionManager;

    ChatIdConverter chatIdConverter;

    public TelegramLongPollingSessionBot(){
        this(new DefaultChatIdConverter());
    }

    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter){
        this.setSessionManager(new DefaultSessionManager());
        this.setChatIdConverter(chatIdConverter);
        AbstractSessionDAO sessionDAO = (AbstractSessionDAO) sessionManager.getSessionDAO();
        sessionDAO.setSessionIdGenerator(chatIdConverter);
    }

    public void setSessionManager(DefaultSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setChatIdConverter(ChatIdConverter chatIdConverter) {
        this.chatIdConverter = chatIdConverter;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Optional<Session> chatSession;
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
        } else {
            chatSession = Optional.empty();
            setAlleAttributes (chatSession);
            onUpdateReceived(update, chatSession);
            return;
        }
        chatIdConverter.setSessionId(message.getChatId());
        chatSession = this.getSession(message);
        if (chatSession.get().getAttribute("SubMenuAttribute") == null
        ) {
            setAlleAttributes (chatSession);
        }
        onUpdateReceived(update, chatSession);
    }

    public Optional<Session> getSession(Message message){
        try {
            return Optional.of(sessionManager.getSession(chatIdConverter));
        } catch (UnknownSessionException e) {
            SessionContext botSession = new DefaultChatSessionContext(message.getChatId(), message.getFrom().getUserName());
            return Optional.of(sessionManager.start(botSession));
        }
    }

    public abstract void onUpdateReceived(Update update, Optional<Session> botSession);

    private void setAlleAttributes (Optional<Session> chatSession){
        chatSession.get().setAttribute("SubMenuAttribute", ISEMPTY);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOTWITHSESSION_USER;
    }
    @Override
    public String getBotToken() {
        return BotConfig.BOTWITHSESSION_TOKEN;
    }
}
