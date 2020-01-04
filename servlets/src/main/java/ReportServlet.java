import dao.*;
import org.slf4j.Logger;
import org.thymeleaf.context.WebContext;
import provider.DBIProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static org.slf4j.LoggerFactory.getLogger;
import static web.ThymeleafListener.engine;


@WebServlet(urlPatterns = "/servlets")
public class ReportServlet extends HttpServlet {
    private static final Logger log = getLogger(DBIProvider.class);

    DateOfOperatorRequestDao daoOperatorD = DBIProvider.getDao(DateOfOperatorRequestDao.class);
    DateOfGivingOfIndicatorsDao daoIndicator = DBIProvider.getDao(DateOfGivingOfIndicatorsDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start doGet of '/servlets'");
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        webContext.setVariable("report", buildReport(req));
        webContext.setVariable("default_start", req.getParameter("start"));
        webContext.setVariable("default_end", req.getParameter("end"));

        engine.process("result", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonData = req.getParameter("userAcc");
        String jsonData1 = req.getParameter("yourZone");
        String jsonData2 = req.getParameter("yourIndicate");
        System.out.println("userAcc = " + jsonData + ", yourZone = " + jsonData1+ ", yourIndicate = " + jsonData2);
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }



    private Report buildReport(HttpServletRequest req){
        Report report = new Report();
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();

        if (req.getParameter("start")==null){
            start = LocalDate.now().with(firstDayOfYear());
        } else if (req.getParameter("start")!=null){
            start = (LocalDateTime.parse(req.getParameter("start")).toLocalDate());
        }

        if (req.getParameter("end")==null){
            end = LocalDate.now().with(lastDayOfYear());
        } else if(req.getParameter("end")!=null){
            end = (LocalDateTime.parse(req.getParameter("end")).toLocalDate());
        }

        report.setStart(start);
        report.setEnd(end);
        report.setIndicators(daoIndicator.getCountBetween(start, end));
        report.setRequest(daoOperatorD.getCountBetween(start, end));
        return report;
    }
    /*@Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
    }

        String start = req.getParameter("start");
        String end = req.getParameter("end");



    */
}
