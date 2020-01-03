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
public class AppRead {
    public static final String LOGTAG = "AppRead";
    public static final String URL = getUrls();
    public static final String TOKEN = getToken();


    private static String getUrls ()
    {
        BotLogger.info(LOGTAG, "Start method getUrl in " +  LOGTAG  );
        Properties prop = null;
        try (InputStream input = AppRead.class.getClassLoader().getResourceAsStream("config.properties")) {
             prop = new Properties();

            if (input == null) {
                throw new IOException("Sorry, unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("urlpost"));

        } catch (IOException ex) {
            ex.printStackTrace();
            BotLogger.error(LOGTAG, ex );
        }
        BotLogger.info(LOGTAG, "End method getUrl in " +  LOGTAG);
        return prop.getProperty("urlpost");

    }

    private static String getToken ()
    {
        BotLogger.info(LOGTAG, "Start method getUrl in " +  LOGTAG  );
        Properties prop = null;
        try (InputStream input = AppRead.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop = new Properties();

            if (input == null) {
                throw new IOException("Sorry, unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("url"));

        } catch (IOException ex) {
            ex.printStackTrace();
            BotLogger.error(LOGTAG, ex );
        }
        BotLogger.info(LOGTAG, "End method getToken in " +  LOGTAG);
        return prop.getProperty("token");
    }


}
