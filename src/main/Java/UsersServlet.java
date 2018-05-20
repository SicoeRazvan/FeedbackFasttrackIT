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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        if (action != null && action.equals("read"))
            read(request, response);
        else if (action != null && action.equals("add")){
            add(request,response);
        }
        else if(action != null && action.equals("delete")){
            delete(request,response);
        }
    }

    private void read(HttpServletRequest request, HttpServletResponse response) {

        DbOperations dbOperations = new DbOperations();
        JSONObject json = new JSONObject();

        try {
            json.put("users", dbOperations.getListOfUsers());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        returnJsonResponse(response, json.toString());
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        DbOperations dbOperations = new DbOperations();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usernameType = request.getParameter("usernameType");

        try {
            dbOperations.addUserToDb(username, password, usernameType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        DbOperations dbOperations = new DbOperations();

        String id = request.getParameter("id");
        int ID = Integer.parseInt(id);

        try {
            dbOperations.deleteUserFromDb(ID);
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
