package edu.cad.controllers;

import com.google.gson.Gson;
import edu.cad.utils.Utils;
import edu.cad.utils.databaseutils.DatabaseCloner;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import edu.cad.utils.databaseutils.DatabaseYears;
import edu.cad.utils.hibernateutils.HibernateSessionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/year-change")
public class YearChangeController extends HttpServlet {
    private DatabaseSwitcher databaseSwitcher;

    @Override
    public void init() throws ServletException {
        super.init();
        this.databaseSwitcher = new DatabaseSwitcher(
                HibernateSessionManager.getInstance(),
                new DatabaseCloner(HibernateSessionManager.getInstance())
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "list":
                    returnList(response);
                    break;
                case "switch":
                    switchDB(request, response);
                    break;
                case "currentYear":
                    getCurrentYear(request, response);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("<%s> action is not supported!", action));
            }
        }
    }

    private void returnList(HttpServletResponse response) throws IOException {
        String filePathOnServer = getServletContext().getRealPath("WEB-INF/classes/DatabaseYears.txt");
        DatabaseYears.setYearsFilePath(filePathOnServer);
        Set<Integer> yearsAvailable = DatabaseYears.getAllYears();
        ArrayList<Integer> years = new ArrayList<>(yearsAvailable);
        years.add(years.get(years.size() - 1) + 1);
        years.trimToSize();

        ArrayList<String> strings = new ArrayList<>();
        for (Integer i : years) {
            strings.add(i.toString());
        }

        writeJsonObjectToResponse(response, years);
    }

    private void switchDB(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        String yearStr = request.getParameter("years");
        if (yearStr != null) {
            if (Utils.isNumber(yearStr)) {
                databaseSwitcher.switchDatabase(Integer.parseInt(yearStr));
            }
        }

        response.sendRedirect("DatabaseYear.html");
    }

    private void getCurrentYear(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int currentDbYear = databaseSwitcher.getDatabaseYear();
        YearResponseDto responseDto = new YearResponseDto(currentDbYear);

        writeJsonObjectToResponse(response, responseDto);
    }

    private void writeJsonObjectToResponse(HttpServletResponse response, Object objectForJson) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(objectForJson));
    }

    private static class YearResponseDto {
        int year;

        YearResponseDto(int year) {
            this.year = year;
        }
    }
}