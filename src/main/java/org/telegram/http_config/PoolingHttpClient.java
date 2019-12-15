package org.telegram.http_config;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.telegram.telegrambots.meta.logging.BotLogger;

@ToString
@NoArgsConstructor
public class PoolingHttpClient {
    private static CloseableHttpClient instance;
    public static final String LOGTAG = "PoolingHttpClient_2";

    public static synchronized CloseableHttpClient getInstance(){
        if(instance == null){
            PoolingHttpClient xxx = new PoolingHttpClient();
            instance = xxx.getCloseableHttpClient();
        }
        return instance;
    }



    private CloseableHttpClient getCloseableHttpClient() {
        BotLogger.info(LOGTAG, "Start method getCloseableHttpClient in " +  LOGTAG  );

        ConnectionKeepAliveStrategy myStrategy = getStrategy();

        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager ();

         CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setKeepAliveStrategy(myStrategy)
                .setConnectionManager(connManager)
                .build();


            BotLogger.debug(LOGTAG, "successful method getCloseableHttpClient in " +  LOGTAG  );
        return closeableHttpClient;
    }

    private ConnectionKeepAliveStrategy getStrategy (){
        BotLogger.info(LOGTAG, "Start method getStrategy in " +  LOGTAG  );

        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 5 * 1000;
            }
        };
    }

    private HttpPost getHttpPost (){
        AppGetUrl appGetUrl = new AppGetUrl();
        HttpPost post = new HttpPost(appGetUrl.getUrl());
        BotLogger.info(LOGTAG, "Grated mAppGetUrl =  " +  appGetUrl.getUrl());
        return post;
    }
}
