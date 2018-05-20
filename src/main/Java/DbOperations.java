import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOperations {


    private final String URL = "jdbc:postgresql://localhost:5432/feedback_fasttrackit";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "12345";

    public int login(String user, String pwd, String userType) {

        int found = -1;
        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement st = conn.createStatement();

            String query = "SELECT id FROM users where username='" + user + "' and password='" + pwd + "'and type='" + userType + "'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);



            while (rs.next()) {
                found = rs.getInt("id");
            }

            // 7. close the objects
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;
    }



    public int register(String user, String pwd) {

        int found = -1;
        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement pSt = conn.prepareStatement("INSERT INTO users (username,password) VALUES (?,?)");
            pSt.setString(1, user);
            pSt.setString(2, pwd);

            int rowsInserted = pSt.executeUpdate();

            pSt.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;
    }

    public List getListOfCourses() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("SELECT id,name, nr_of_courses FROM courses");
        ResultSet rs = pSt.executeQuery();

        List<Courses> productsList = new ArrayList<>();
        while (rs.next()) {
            Courses c = new Courses();

            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setLabs(rs.getInt("nr_of_courses"));
            productsList.add(c);
        }

        pSt.close();
        conn.close();

        return productsList;
    }

    public void sendFeedbackToDb(int courseId, int lab, String comment) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("INSERT INTO feedback (courseid,comment,labnr) VALUES (?,?,?)");
        pSt.setInt(1, courseId);
        pSt.setString(2, comment);
        pSt.setInt(3, lab);

        int rowsInserted = pSt.executeUpdate();

        pSt.close();

        conn.close();
    }

    public List getListOfFeedback() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("Select feedback.id, courses.name, feedback.comment, feedback.labnr \n" +
                "From feedback \n" +
                "Join courses on courses.id = feedback.courseid");
        ResultSet rs = pSt.executeQuery();

        List<Feedback> feedbackList = new ArrayList<>();
        while (rs.next()) {
            Feedback f = new Feedback();

            f.setId(rs.getLong("id"));
            f.setCourseName(rs.getString("name"));
            f.setComment(rs.getString("comment"));
            f.setLabNr(rs.getInt("labnr"));
            feedbackList.add(f);
        }

        pSt.close();
        conn.close();

        return feedbackList;
    }

    public List getListOfFeedbackByCourseType(String courseName) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("Select feedback.id, courses.name, feedback.comment, feedback.labnr \n" +
                "From feedback \n" +
                "Join courses on courses.id = feedback.courseid \n" +
                "Where  courses.name = '" + courseName + "' \n" +
                "Order by feedback.labnr");
        ResultSet rs = pSt.executeQuery();

        List<Feedback> feedbackList = new ArrayList<>();
        while (rs.next()) {
            Feedback f = new Feedback();

            f.setId(rs.getLong("id"));
            f.setCourseName(rs.getString("name"));
            f.setComment(rs.getString("comment"));
            f.setLabNr(rs.getInt("labnr"));
            feedbackList.add(f);
        }

        pSt.close();
        conn.close();

        return feedbackList;
    }

    public void deleteFeedbackFromDb(int id) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("DELETE FROM feedback WHERE id=?");
        pSt.setInt(1, id);

        int rowsInserted = pSt.executeUpdate();

        pSt.close();

        conn.close();
    }

    public List sortFeedback(String courseName, int labNr) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("Select feedback.id, courses.name, feedback.comment, feedback.labnr \n" +
                "From feedback \n" +
                "Join courses on courses.id = feedback.courseid \n" +
                "Where corses.name='" + courseName + "'and feedback.labnr='" + labNr + "'");
        ResultSet rs = pSt.executeQuery();

        List<Feedback> feedbackList = new ArrayList<>();
        while (rs.next()) {
            Feedback f = new Feedback();

            f.setId(rs.getLong("id"));
            f.setCourseName(rs.getString("name"));
            f.setComment(rs.getString("comment"));
            f.setLabNr(rs.getInt("labnr"));
            feedbackList.add(f);
        }

        pSt.close();
        conn.close();

        return feedbackList;
    }

    public List getListOfUsers() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("select id, username, password, type from users");
        ResultSet rs = pSt.executeQuery();

        List<Users> usersList = new ArrayList<>();
        while (rs.next()) {
            Users u = new Users();

            u.setId(rs.getLong("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setType(rs.getString("type"));
            usersList.add(u);
        }

        pSt.close();
        conn.close();

        return usersList;
    }

    public void addUserToDb(String username, String password, String usernameType) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("Insert Into users (username, password, type) VALUES (?,?,?)");
        pSt.setString(1, username);
        pSt.setString(2, password);
        pSt.setString(3, usernameType);

        int rowsInserted = pSt.executeUpdate();

        pSt.close();

        conn.close();
    }

    public void deleteUserFromDb(int id) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pSt = conn.prepareStatement("DELETE FROM users WHERE id=?");
        pSt.setInt(1, id);

        int rowsInserted = pSt.executeUpdate();

        pSt.close();

        conn.close();
    }
}
