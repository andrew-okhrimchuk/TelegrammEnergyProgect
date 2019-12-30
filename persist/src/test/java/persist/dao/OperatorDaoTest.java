package persist.dao;

import dao.DateOfOperatorRequestDao;
import model.DateOfOperatorRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persist.OperatorTestData;

import java.time.LocalDate;
import java.util.List;

import static persist.OperatorTestData.*;


public class OperatorDaoTest extends AbstractDaoTest<DateOfOperatorRequestDao> {

    public OperatorDaoTest() {
        super(DateOfOperatorRequestDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        OperatorTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        OperatorTestData.setUp();
    }

    @Test
    public void getAllCountGiving() {
        dao.insert(dateOfOperatorRequest1);
        int allCount = dao.getAllCount();
        System.out.println(allCount);
        Assert.assertTrue(allCount > 0);
    }

    @Test
    public void getCountBetweenDate() {

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        int allCount = dao.getCountBetween(start, end);
        Assert.assertTrue(allCount == 3);

        dao.insert(dateOfOperatorRequestMinusOneDay);
        int allCount2 = dao.getCountBetween(start, end);
        Assert.assertTrue(allCount2 == 4);

        dao.insert(dateOfOperatorRequestMinusTwoDays);
        int allCount3 = dao.getCountBetween(start, end);
        Assert.assertTrue(allCount3 == 4);
    }

    @Test
    public void getWithIdTelegramDate() {
        DateOfOperatorRequest someone = dao.getWithIdTelegramDate(dateOfOperatorRequest1.getId_telegram(), dateOfOperatorRequest1.getDate());
        Assert.assertEquals(someone, dateOfOperatorRequest1);
    }

    @Test
    public void getWithIdTelegramCheckDouble() {
        dao.insert(dateOfOperatorRequestMinusOneDay);
        dao.insert(dateOfOperatorRequestMinusOneDay);
        dao.insert(dateOfOperatorRequestMinusOneDay);
        dao.insert(dateOfOperatorRequestMinusOneDay);
        dao.insert(dateOfOperatorRequestMinusTwoDays);
        dao.insert(dateOfOperatorRequestMinusTwoDays);
        dao.insert(dateOfOperatorRequestMinusTwoDays);
        dao.insert(dateOfOperatorRequestMinusTwoDays);
        DateOfOperatorRequest dateOfGivingOfIndicators = dao.getWithIdTelegramDate(dateOfOperatorRequestMinusOneDay.getId_telegram(), dateOfOperatorRequestMinusOneDay.getDate());
        Assert.assertEquals(dateOfOperatorRequestMinusOneDay, dateOfGivingOfIndicators);
    }

    @Test
    public void getWithIdTelegram() {
        dao.insert(dateOfOperatorRequestMinusOneDay);
        dao.insert(dateOfOperatorRequestMinusTwoDays);
        List<DateOfOperatorRequest> someone = dao.getWithIdTelegram(dateOfOperatorRequestMinusOneDay.getId_telegram());
        Assert.assertEquals(someone, TWO_OfOperatorRequest);
    }
}