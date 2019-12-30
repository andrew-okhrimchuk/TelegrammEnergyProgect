package dao;

//import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import com.github.rkmk.mapper.CustomMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import model.User;


public abstract class UserDao implements AbstractDao {

    public User insert(User user) {
        if (user.isNew()) {
            int id = insertGeneratedId(user);
            user.setId(id);
        } else {
            insertWitId(user);
        }
        return user;
    }

    @SqlUpdate("INSERT INTO users (id_telegram, acc) VALUES (:id_telegram, :acc)")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean User user);

    @SqlUpdate("INSERT INTO users (id, id_telegram, acc) VALUES (:id, :id_telegram, :acc)")
    abstract void insertWitId(@BindBean User user);

    @SqlQuery("SELECT * FROM users WHERE id_telegram = :id_telegram")
    public abstract User getWithUser(@Bind ("id_telegram") int id_telegram);

    @SqlQuery("SELECT * FROM users WHERE id_telegram = :id_telegram")
    public abstract User getWithUser(@BindBean User user);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users")
    @Override
    public abstract void clean();

}
