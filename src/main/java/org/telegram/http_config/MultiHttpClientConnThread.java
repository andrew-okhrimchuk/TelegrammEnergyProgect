package org.telegram.http_config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.io.IOException;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultiHttpClientConnThread extends Thread {
    private CloseableHttpClient client;
    private HttpPost post;
    public static final String LOGTAG = "MultiHttpClientConnThread";

    public void run(){
        BotLogger.info(LOGTAG, "Srart method run in " +  LOGTAG + ". currentThread().getName() = " + currentThread().getName());

        try {
            CloseableHttpResponse response = client.execute(post);
        //    System.out.println(EntityUtils.toString(response.getEntity()));
            BotLogger.info(LOGTAG, EntityUtils.toString(response.getEntity())) ;
            EntityUtils.consume(response.getEntity());
        } catch (ClientProtocolException ex) {
            BotLogger.error(LOGTAG, "Catch ClientProtocolExceptionError in method run, in " +  LOGTAG + ". currentThread().getName() = " + currentThread().getName());

        } catch (IOException ex) {
            BotLogger.error(LOGTAG, "Catch IOException in method run, in " +  LOGTAG + ". currentThread().getName() = " + currentThread().getName());

        }
        BotLogger.info(LOGTAG, "End method run in " +  LOGTAG + ". currentThread().getName() = " + currentThread().getName());
    }
}
