package readConrext;


import dao.DateOfOperatorRequestDao;
import dao.UserDao;
import org.junit.Test;
import provider.DBIProvider;

public class UserDaoTest  {

    @Test
    public void getUser() {

        DateOfOperatorRequestDao daoOperatorD = DBIProvider.getDao(DateOfOperatorRequestDao.class);
    }

    @Test
    public void getUser2() {

        DateOfOperatorRequestDao daoOperatorD = DBIProvider.getDao(DateOfOperatorRequestDao.class);
        daoOperatorD.getAllCount();
    }

}