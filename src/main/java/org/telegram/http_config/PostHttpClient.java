package org.telegram.http_config;

import org.apache.http.client.methods.HttpPost;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class PostHttpClient {
    private static HttpPost instance;
    public static final String LOGTAG = "PostHttpClient";

    public static synchronized HttpPost getInstance(){
        if(instance == null){
            PostHttpClient xxx = new PostHttpClient();
            instance = xxx.getHttpPost();
        }
        return instance;
    }


    private HttpPost getHttpPost (){
        AppGetUrl appGetUrl = new AppGetUrl();
        HttpPost post = new HttpPost(appGetUrl.getUrl());
        BotLogger.info(LOGTAG, "Grated mAppGetUrl =  " +  appGetUrl.getUrl());
        return post;
    }

}
