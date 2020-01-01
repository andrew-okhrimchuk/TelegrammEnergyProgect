import DTO.Report;
import dao.*;
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
import java.time.ZoneId;
import java.util.Date;

import static web.ThymeleafListener.engine;


@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class ReportServlet extends HttpServlet {
    DateOfOperatorRequestDao daoOperatorD = DBIProvider.getDao(DateOfOperatorRequestDao.class);
    DateOfGivingOfIndicatorsDao daoIndicator = DBIProvider.getDao(DateOfGivingOfIndicatorsDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        String start = req.getParameter("start");
        String end = req.getParameter("end");
        Report report = new Report();
        report.setStart(LocalDate.now());
        report.setEnd(LocalDate.now());
        report.setIndicators(daoIndicator.getAllCount());
        report.setRequest(daoOperatorD.getAllCount());
        webContext.setVariable("report", report);
        engine.process("result", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());

        try {
//            http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
            Part filePart = req.getPart("fileToUpload");
            if (filePart.getSize() == 0) {
                throw new IllegalStateException("Upload file have not been selected");
            }
            try (InputStream is = filePart.getInputStream()) {
               /* List<User> users = userProcessor.process(is);
                webContext.setVariable("users", users);
                engine.process("result", webContext, resp.getWriter());*/
            }
        } catch (Exception e) {
            webContext.setVariable("exception", e);
            engine.process("exception", webContext, resp.getWriter());
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
