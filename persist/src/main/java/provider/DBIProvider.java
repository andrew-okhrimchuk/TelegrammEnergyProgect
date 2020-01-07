package provider;

import com.github.rkmk.mapper.CustomMapperFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
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
import java.net.URI;
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

                    URI dbUri = new URI(System.getenv("DATABASE_URL"));

                    String username = dbUri.getUserInfo().split(":")[0];
                    String password = dbUri.getUserInfo().split(":")[1];
                    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

                    Properties props = new Properties();
                    props.setProperty("ssl","true");
                    props.setProperty("sslmode","verify-ca");
                    props.setProperty("sslfactory","org.postgresql.ssl.NonValidatingFactory");

                    ComboPooledDataSource cpds = new ComboPooledDataSource();
                    cpds.setProperties( props );
                    cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
                     cpds.setJdbcUrl(dbUrl );
                    cpds.setUser(username);
                    cpds.setPassword(password);
                   /* cpds.setJdbcUrl( "jdbc:postgresql://ec2-54-246-121-32.eu-west-1.compute.amazonaws.com:5432/de6vdd91oitr14" );
                    cpds.setUser("poyegifpaqwrhl");
                    cpds.setPassword("54846d99b101407d1e0f9a34782e8edce96f288cfd0fb980fbb938db943b1364");
*/

// the settings below are optional -- c3p0 can work with defaults
                    cpds.setMinPoolSize(5);
                    cpds.setAcquireIncrement(5);
                    cpds.setMaxPoolSize(20);
                    cpds.setMaxIdleTime(10000);

                    dbi = new DBI(cpds);
                    /*InitialContext ctx = new InitialContext();
                    dbi = new DBI((DataSource) ctx.lookup("java:/comp/env/jdbc/postgres"));*/

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
