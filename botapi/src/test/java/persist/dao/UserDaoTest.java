package persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persist.UserTestData;
import persist.model.User;


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
    public void getWithLimit() {
        List<User> users = dao.getWithLimit(5);
        Assert.assertEquals(FIST5_USERS, users);
    }

    @Test
    public void insertAllWitId() {
        dao.clean();
      //  List<User> toInsert = FIST5_USERS;
        List<User> toInsert = new ArrayList<>();
        toInsert.add(USER3);
        toInsert.add(USER2);
        toInsert.add(USER1);
        //  dao.insertAllWitId(toInsert.iterator(), toInsert.size());
        int[] usersId = dao.insertAllWitId(toInsert.iterator(), toInsert.size());

        for (int xx :
                usersId) {
            System.out.println(xx);
        }

        toInsert = FIST5_USERS;
        int[] usersId2 = dao.insertAllWitId(toInsert.iterator(), toInsert.size());

        for (int xx :
                usersId2) {
            System.out.println(xx);
        }
        //Assert.assertEquals(FIST5_USERS, users);
    }
    @Test
    public void getWithLimit1() {

        String s = dao.insertWitId1(ADMIN);
        System.out.println(s);
    }
}