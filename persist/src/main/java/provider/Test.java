package provider;

import dao.UserDao;

public class Test {
    public static void main (String [] aaa){
        UserDao userDao = DBIProvider.getDao(UserDao.class);
    }

}
