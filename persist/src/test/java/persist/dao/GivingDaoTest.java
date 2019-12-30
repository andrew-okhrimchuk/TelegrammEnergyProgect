package persist.dao;

import dao.DateOfGivingOfIndicatorsDao;
import model.DateOfGivingOfIndicators;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persist.GivingTestData;
import java.time.LocalDate;
import java.util.List;

import static persist.GivingTestData.*;


public class GivingDaoTest extends AbstractDaoTest<DateOfGivingOfIndicatorsDao> {

    public GivingDaoTest() {
        super(DateOfGivingOfIndicatorsDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        GivingTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        GivingTestData.setUp();
    }

    @Test
    public void getAllCountGiving() {
        dao.insert(dateOfGivingOfIndicators1);
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

        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        int allCount2 = dao.getCountBetween(start, end);
        Assert.assertTrue(allCount2 == 4);

        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        int allCount3 = dao.getCountBetween(start, end);
        Assert.assertTrue(allCount3 == 4);
    }

    @Test
    public void getWithIdTelegram() {
        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        List<DateOfGivingOfIndicators> allCount = dao.getWithIdTelegram(dateOfGivingOfIndicatorsMinusOneDay.getId_telegram());
        Assert.assertEquals(TWO_DateOfGivingOfIndicatorsS, allCount);
    }

    @Test
    public void getWithIdTelegramDouble() {
        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        dao.insert(dateOfGivingOfIndicatorsMinusOneDay);
        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        dao.insert(dateOfGivingOfIndicatorsMinusTwoDays);
        DateOfGivingOfIndicators dateOfGivingOfIndicatorsMinusOne = dao.getWithIdTelegram(dateOfGivingOfIndicatorsMinusOneDay.getId_telegram(), dateOfGivingOfIndicatorsMinusOneDay.getDate());
        Assert.assertEquals(dateOfGivingOfIndicatorsMinusOneDay, dateOfGivingOfIndicatorsMinusOne);
    }
}