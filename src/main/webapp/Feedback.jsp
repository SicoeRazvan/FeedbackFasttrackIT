<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("adminId");
    if (o == null) {
        response.sendRedirect("AdminLogin.html");
    }
%>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Feedback</title>
    <link href="Css/FeedbackPages.css" rel="stylesheet" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div id="header">
    <div id="logo">
        <img src="https://www.fasttrackit.org/wp-content/uploads/2017/08/Fastrackit-1.png">
    </div>
    <h1>Feedback</h1>
</div>

<div id="menu">
    <ul>
        <li><a href="Feedback.jsp">Feedback</a></li>
        <li><a href="Users.jsp">Users</a></li>
    </ul>
</div>

<div id="content">
    <div id="coursesContainer">
        <div id="coursesContainerTitle">
            <p>Courses list</p>
        </div>

        <div id="coursesName">
            <ul>
                <li><a href="Database.jsp">Database</a></li>
                <li><a href="C-Sharp.jsp">C Sharp</a></li>
                <li><a href="Java.jsp">Java</a></li>
                <li><a href="QaAutomation.jsp">Qa Automation</a></li>
                <li><a href="QaManualTesting.jsp">Qa Manual</a></li>
                <li><a href="WebDeveloper.jsp">Web Developer</a></li>
            </ul>
        </div>
    </div>

    <div id="feedbackContainer">
        <div id="feedbackContainerTitle">
            <p>Feedback to all Courses</p>
        </div>

        <div id="comments">
            <div>
                <ol></ol>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    function loadComments() {
        $.ajax({
            url: 'feedback?action=read&course=all'
        }).done(function (response) {
            putFeedbackInHTML(response.allcourses);
        });
    }

    function putFeedbackInHTML(feedback) {
        var divCourses = document.getElementById('comments');
        ol = document.getElementsByTagName('ol')[0];
        var list = '';
        for (var i = 0; i < feedback.length; i++) {
            var id = feedback[i].id;
            var comments = feedback[i].comment;
            var lab = feedback[i].labNr;
            var courseName = feedback[i].courseName;
            var feedbacks = '<div class="comment">' +
                '<div class="commentCourseNameArea"> ' +
                '<p>' + 'Course: ' + courseName + '</p>' +
                '</div>' +
                '<div class="deleteComment"> ' +
                '<button class="deleteButton" onclick="deleteComment(' + id + ')">Delete</button>' +
                '</div>' +
                '<div class="commentLabArea">' +
                '<p>' + 'Lab: ' + lab + '</p>' +
                '</div>' +
                '<div class="commentTextArea"> ' +
                '<p>' + 'Feedback:' + '</p>' +
                '<p>' + comments + '</p>' +
                '</div>' +
                '</div>';

            list = list + feedbacks;
        }
        ol.innerHTML = list;
        $(feedback)
    }

    loadComments();

    function deleteComment(id) {
        $.ajax({
            url: 'feedback?action=delete&id=' + id
        }).done(function (response) {
            location.href = "Feedback.jsp";
        });
    }
</script>
</html>