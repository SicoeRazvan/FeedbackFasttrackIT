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

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        if (action != null && action.equals("read")) {
            read(request, response);
        }
        else if(action != null && action.equals("read")){
            sort(request, response);
        }
        else if(action != null & action.equals("delete")){
            delete(request, response);
        }
    }

    private void read(HttpServletRequest request, HttpServletResponse response) {
        DbOperations dbOperations = new DbOperations();
        JSONObject json = new JSONObject();
        String course = request.getParameter("course");

        if (course != null && course.equals("all")) {
            try {
                json.put("allcourses", dbOperations.getListOfFeedback());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (course != null && course.equals("Qa Testare")) {
            try {
                json.put("qaTestare", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (course != null && course.equals("Java")) {
            try {
                json.put("java", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (course != null && course.equals("Database")) {
            try {
                json.put("database", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (course != null && course.equals("Qa Automation")) {
            try {
                json.put("qaAutomation", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (course != null && course.equals("C Sharp")) {
            try {
                json.put("cSharp", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (course != null && course.equals("Web Developer")) {
            try {
                json.put("webDeveloper", dbOperations.getListOfFeedbackByCourseType(course));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        returnJsonResponse(response, json.toString());
    }

    private void sort(HttpServletRequest request, HttpServletResponse response){
        DbOperations dbOperations = new DbOperations();
        JSONObject json = new JSONObject();

        String courseName = request.getParameter("courseName");
        String labNr = request.getParameter("labNr");

        int labNumber = Integer.parseInt(labNr);

        try {
            json.put("sortedFeedback", dbOperations.sortFeedback(courseName,labNumber));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        returnJsonResponse(response, json.toString());
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        DbOperations dbOperations = new DbOperations();

        String id = request.getParameter("id");
        int ID = Integer.parseInt(id);

        try {
             dbOperations.deleteFeedbackFromDb(ID);
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
