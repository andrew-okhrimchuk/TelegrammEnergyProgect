package persist;

import com.google.common.collect.ImmutableList;
import dao.UserDao;
import model.User;
import provider.DBIProvider;

import java.util.List;

public class UserTestData {

    public static User USER1;
    public static User USER2;
    public static User USER3;
    public static List<User> FIST5_USERS;

    public static void init() {

        USER1 = new User(1, 111111, new Integer[]{212345678});
        USER2 = new User(222222, new Integer[]{412345678,212345677});
        USER3 = new User(3, 333333, new Integer[]{444444444});
        FIST5_USERS = ImmutableList.of(USER1, USER2);
    }

    public static void setUp() {
        UserDao dao = DBIProvider.getDao(UserDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIST5_USERS.forEach(dao::insert);
            dao.insert(USER3);
        });
    }
}
