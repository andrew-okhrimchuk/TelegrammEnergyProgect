package provider;

import com.github.rkmk.mapper.CustomMapperFactory;
import dao.AbstractDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.slf4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

public class DBIProvider2 {
    private static final Logger log = getLogger(DBIProvider2.class);

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

                    InitialContext ctx = new InitialContext();
                    dbi = new DBI((DataSource) ctx.lookup("java:/comp/env/jdbc/postgres"));

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
        DBIProvider2.connectionFactory = connectionFactory;
    }

    public static DBI getDBI() {
        return DBIHolder.jDBI;
    }

    public static <T extends AbstractDao> T getDao(Class<T> daoClass) {
        return DBIHolder.jDBI.onDemand(daoClass);
    }



}
