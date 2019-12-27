package org.telegram.http_config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ToString
@Getter
@Setter
public class AppReadUrl {
    public static final String LOGTAG = "App";
    private static AppReadUrl instance;
    private String url ;

    public AppReadUrl() {
        this.url = getUrls();
    }

    public static synchronized AppReadUrl getInstance(){
        if(instance == null){
            instance = new AppReadUrl();
        }
        return instance;
    }

    private String getUrls ()
    {
        BotLogger.info(LOGTAG, "Start method getUrl in " +  LOGTAG  );
        Properties prop = null;
        try (InputStream input = AppReadUrl.class.getClassLoader().getResourceAsStream("config.properties")) {
             prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("url"));

        } catch (IOException ex) {
            ex.printStackTrace();
            BotLogger.error(LOGTAG, ex );
        }
        BotLogger.info(LOGTAG, "End method getUrl in " +  LOGTAG);
        return prop.getProperty("url");

    }
}
