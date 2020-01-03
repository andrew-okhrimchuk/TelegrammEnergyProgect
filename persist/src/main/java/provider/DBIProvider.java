package provider;

import com.github.rkmk.mapper.CustomMapperFactory;
import dao.DateOfGivingOfIndicatorsDao;
import dao.DateOfOperatorRequestDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.slf4j.Logger;
import dao.AbstractDao;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class DBIProvider {
    private static final Logger log = getLogger(DBIProvider.class);

    private volatile static ConnectionFactory connectionFactory = null;

    private static class DBIHolder {
        static final DBI jDBI;

        static {

            final DBI dbi;
            //File file = new File(getClass().getResource("/").getPath() + "../resourses/" + file;"database.properties");

            if (connectionFactory != null) {
                log.info("Init jDBI with  connectionFactory");
                dbi = new DBI(connectionFactory);
            }
            else {
                try {log.info("Init jDBI with  JNDI");

                    Properties prop = new Properties();
                    //prop.load(new FileReader(file));
                  // prop.load(DBIProvider.class.getResourceAsStream("database.properties"));
                    prop.load(DBIProvider.class.getResourceAsStream("/database.properties"));


                    //InitialContext ctx = new InitialContext();
                    //dbi = new DBI((DataSource) ctx.lookup("java:/comp/env/jdbc/postgres"));
                    dbi = new DBI(prop.getProperty("url"), prop);
                } catch (Exception ex) {
                    log.error("PostgreSQL initialization failed", ex);
                    System.out.println(ex);
                    throw new IllegalStateException("PostgreSQL initialization failed", ex);
                }
            }

            jDBI = dbi;
            jDBI.setSQLLog(new SLF4JLog());
            jDBI.registerArgumentFactory(new IntArrayArgument());
            CustomMapperFactory mapper = new CustomMapperFactory();
            mapper.register(new LocalDateMapperFactory());
            jDBI.registerMapper(mapper);
        }
    }

    public static void init(ConnectionFactory connectionFactory) {
        DBIProvider.connectionFactory = connectionFactory;
    }

    public static DBI getDBI() {
        return DBIHolder.jDBI;
    }

    public static <T extends AbstractDao> T getDao(Class<T> daoClass) {
        return DBIHolder.jDBI.onDemand(daoClass);
    }


}
