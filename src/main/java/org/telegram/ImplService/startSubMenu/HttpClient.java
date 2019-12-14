package org.telegram.ImplService.startSubMenu;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.session.Session;
import org.telegram.config.AppGetUrl;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static final String LOGTAG = "HttpClient";

    protected void sendPost(Optional<Session> optionalSession) throws Exception {
        BotLogger.info(LOGTAG, "Start method sendPost in " +  LOGTAG  );

        if (!optionalSession.isPresent()){
            return;
        }
        AppGetUrl appGetUrl = new AppGetUrl();
        HttpPost post = new HttpPost(appGetUrl.getUrl());
        BotLogger.info(LOGTAG, "Grated mAppGetUrl =  " +  appGetUrl.getUrl());

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("userId", (String) optionalSession.get().getAttribute("YOUR_ACCOUNT")));
        urlParameters.add(new BasicNameValuePair("yourZoneAttribute", (String) optionalSession.get().getAttribute("YOUR_ACCOUNT")));
        urlParameters.add(new BasicNameValuePair("yourIndicate", (String) optionalSession.get().getAttribute("YOUR_Indicate")));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        BotLogger.info(LOGTAG, "End method sendPost in " +  LOGTAG  );

    }

    protected void close() throws IOException {
        httpClient.close();
    }
}
