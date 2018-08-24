package edu.cad.servlets;

import com.google.gson.Gson;
import edu.cad.utils.Utils;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import edu.cad.utils.databaseutils.DatabaseYears;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet("/YearChangeController")
public class YearChangeController extends HttpServlet {
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
            switch(action) {
                case "list":
                    returnList(response);
                    break;
                case "switch":
                    switchDB(request, response);
            }
        }
    }

    private void returnList(HttpServletResponse response) throws IOException {
        String filePathOnServer = getServletContext().getRealPath("WEB-INF/classes/DatabaseYears.txt");
        DatabaseYears.setYearsFilePath(filePathOnServer);
        Set<Integer> yearsAvailable = DatabaseYears.getAllYears();
        ArrayList<Integer> years = new ArrayList<>(yearsAvailable);
        years.add(years.get(years.size()-1) + 1);
        years.trimToSize();
        
        ArrayList<String> strings = new ArrayList<>();
        for (Integer i : years) {
            strings.add(i.toString());
        }
        
        response.setContentType("application/json");  
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(years));
    }

    private void switchDB(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        String yearStr = request.getParameter("years");
        if (yearStr != null) {
            if (Utils.isNumber(yearStr)) {
                DatabaseSwitcher.switchDatabase(Integer.parseInt(yearStr));
            }   
        }
        
        response.sendRedirect("DatabaseYear.jsp");
    }
}
