import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userType = request.getParameter("type");

        if (userType != null && userType.equals("user"))
            userLogin(request, response);
        else if (userType != null && userType.equals("admin"))
            adminLogin(request, response);
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String u = request.getParameter("u");
        String p = request.getParameter("p");
        String userType = "user";

        DbOperations dbOperations = new DbOperations();
        int value = dbOperations.login(u, p, userType);

        if(value!=-1) {
            HttpSession session = request.getSession();
            session.setAttribute("userid", value);
            session.setAttribute("type", "user");
            response.sendRedirect("index.jsp");
        }
        else
        {
            String back = "/Login.html";
            HttpSession session = request.getSession();
            session.removeAttribute("userid");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(back);
            dispatcher.forward(request, response);
        }
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String u = request.getParameter("u");
        String p = request.getParameter("p");
        String userType = "admin";

        DbOperations dbOperations = new DbOperations();
        int value = dbOperations.login(u, p, userType);

        if(value!=-1) {
            HttpSession session = request.getSession();
            session.setAttribute("adminId", value);
            session.setAttribute("type", "admin");
            response.sendRedirect("Feedback.jsp");
        }
        else
        {
            String back = "/AdminLogin.html";
            HttpSession session = request.getSession();
            session.removeAttribute("userId");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(back);
            dispatcher.forward(request, response);
        }
    }
}
