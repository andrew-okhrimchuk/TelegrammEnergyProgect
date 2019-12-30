package persist;

import com.google.common.collect.ImmutableList;
import dao.DateOfOperatorRequestDao;
import model.DateOfOperatorRequest;
import provider.DBIProvider;

import java.time.LocalDate;
import java.util.List;

public class OperatorTestData {

    public static DateOfOperatorRequest dateOfOperatorRequest1;
    public static DateOfOperatorRequest dateOfOperatorRequest2;
    public static DateOfOperatorRequest dateOfOperatorRequest3;
    public static DateOfOperatorRequest dateOfOperatorRequestMinusOneDay;
    public static DateOfOperatorRequest dateOfOperatorRequestMinusTwoDays;
    public static List<DateOfOperatorRequest> FIST5_OfOperatorRequest;
    public static List<DateOfOperatorRequest> TWO_OfOperatorRequest;

    public static void init() {

        dateOfOperatorRequest1 = new DateOfOperatorRequest( 111111, LocalDate.now());
        dateOfOperatorRequest2 = new DateOfOperatorRequest(222222, LocalDate.now());
        dateOfOperatorRequest3 = new DateOfOperatorRequest( 333333, LocalDate.now());
        dateOfOperatorRequestMinusOneDay = new DateOfOperatorRequest( 444444, LocalDate.now().minusDays(1));
        dateOfOperatorRequestMinusTwoDays = new DateOfOperatorRequest( 444444, LocalDate.now().minusDays(2));
        FIST5_OfOperatorRequest = ImmutableList.of(dateOfOperatorRequest1, dateOfOperatorRequest2);
        TWO_OfOperatorRequest = ImmutableList.of(dateOfOperatorRequestMinusOneDay, dateOfOperatorRequestMinusTwoDays);
    }

    public static void setUp() {
        DateOfOperatorRequestDao dao = DBIProvider.getDao(DateOfOperatorRequestDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIST5_OfOperatorRequest.forEach(dao::insert);
            dao.insert(dateOfOperatorRequest3);
        });
    }
}
