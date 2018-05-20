
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        if (action != null && action.equals("read")) {
            read(request, response);
        } else if (action != null && action.equals("write")) {
            write(request, response);
        }
    }

    private void read(HttpServletRequest request, HttpServletResponse response) {

        DbOperations dbOperations = new DbOperations();
        JSONObject json = new JSONObject();

        try {
            json.put("courses", dbOperations.getListOfCourses());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        returnJsonResponse(response, json.toString());
    }

    private void write(HttpServletRequest request, HttpServletResponse response) {
        DbOperations dbOperations = new DbOperations();
        String course = request.getParameter("course");
        String labs = request.getParameter("labs");
        String comment = request.getParameter("comment");

        int labsInt = Integer.parseInt(labs);
        int courseId = Integer.parseInt(course);
        try {
            dbOperations.sendFeedbackToDb(courseId, labsInt, comment);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
