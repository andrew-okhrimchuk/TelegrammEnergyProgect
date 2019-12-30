package persist;

import com.google.common.collect.ImmutableList;
import dao.DateOfGivingOfIndicatorsDao;
import model.DateOfGivingOfIndicators;
import provider.DBIProvider;

import java.time.LocalDate;
import java.util.List;

public class GivingTestData {

    public static DateOfGivingOfIndicators dateOfGivingOfIndicators1;
    public static DateOfGivingOfIndicators dateOfGivingOfIndicators2;
    public static DateOfGivingOfIndicators dateOfGivingOfIndicators3;
    public static DateOfGivingOfIndicators dateOfGivingOfIndicatorsMinusOneDay;
    public static DateOfGivingOfIndicators dateOfGivingOfIndicatorsMinusTwoDays;
    public static List<DateOfGivingOfIndicators> FIST5_DateOfGivingOfIndicatorsS;
    public static List<DateOfGivingOfIndicators> TWO_DateOfGivingOfIndicatorsS;

    public static void init() {

        dateOfGivingOfIndicators1 = new DateOfGivingOfIndicators( 111111, LocalDate.now());
        dateOfGivingOfIndicators2 = new DateOfGivingOfIndicators(1,222222, LocalDate.now());
        dateOfGivingOfIndicators3 = new DateOfGivingOfIndicators( 333333, LocalDate.now());
        dateOfGivingOfIndicatorsMinusOneDay = new DateOfGivingOfIndicators( 444444, LocalDate.now().minusDays(1));
        dateOfGivingOfIndicatorsMinusTwoDays = new DateOfGivingOfIndicators( 444444, LocalDate.now().minusDays(2));
        FIST5_DateOfGivingOfIndicatorsS = ImmutableList.of(dateOfGivingOfIndicators1, dateOfGivingOfIndicators2);
        TWO_DateOfGivingOfIndicatorsS = ImmutableList.of(dateOfGivingOfIndicatorsMinusOneDay, dateOfGivingOfIndicatorsMinusTwoDays);
    }

    public static void setUp() {
        DateOfGivingOfIndicatorsDao dao = DBIProvider.getDao(DateOfGivingOfIndicatorsDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIST5_DateOfGivingOfIndicatorsS.forEach(dao::insert);
            dao.insert(dateOfGivingOfIndicators3);
        });
    }
}
