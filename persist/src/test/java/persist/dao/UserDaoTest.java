package persist.dao;

import dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persist.UserTestData;
import model.User;


import java.util.ArrayList;
import java.util.List;

import static persist.UserTestData.*;


public class UserDaoTest extends AbstractDaoTest<UserDao> {

    public UserDaoTest() {
        super(UserDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        UserTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        UserTestData.setUp();
    }

    @Test
    public void getUser() {
        User user = dao.getWithUser(USER1.getId_telegram());
        Assert.assertEquals(USER1, user);
    }
    @Test
    public void getUserBean() {
        User user = dao.getWithUser(USER1);
        Assert.assertEquals(USER1, user);
    }
}