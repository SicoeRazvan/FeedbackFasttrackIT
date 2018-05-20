<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");
    if (o == null) {
        response.sendRedirect("Login.html");
    }
%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link href="Css/style.css" rel="stylesheet" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div id="header">
    <div id="logo">
        <img src="https://www.fasttrackit.org/wp-content/uploads/2017/08/Fastrackit-1.png">
    </div>
    <h1>Feedback</h1>
</div>
<div id="content">
    <div id="title">
        <p>Please send us your opinion about our courses</p>
    </div>

    <div id="sendFeedback">
        <p>Select Course</p>
        <div id="listOfCourses"></div>
        <p>Select Lab</p>
        <div id="nrOfLabs"></div>
        <p>Your Feedback is:</p>
        <div>
            <textarea rows="4" cols="50" id="comment"></textarea>
        </div>
        <div id="sendButton">
            <button type="button" onclick="sendFeedback()">Send Feedback</button>
        </div>

    </div>

</div>


<div id="footer">

</div>
</body>

<script>
    function loadCouses() {
        $.ajax({
            url: 'courses?action=read'
        }).done(function (response) {
            putCoursesInASelect(response.courses);
            generateLabs();
        });
    }

    function putCoursesInASelect(courses) {
        var divCourses = document.getElementById('listOfCourses');

        var selectList = document.createElement("select");
        selectList.id = "coursesSelect";
        divCourses.appendChild(selectList);

        for (var i = 0; i < courses.length; i++) {
            var option = document.createElement("option");
            option.id = courses[i].name;
            option.value = courses[i].id;
            option.text = courses[i].name;
            selectList.appendChild(option);
        }
    }

    function generateLabs() {
        var divCourses = document.getElementById('nrOfLabs');

        var selectList = document.createElement("select");
        selectList.id = "labsSelect";
        divCourses.appendChild(selectList);

        for (var i = 1; i < 25; i++) {
            var option = document.createElement("option");
            option.value = i;
            option.text = i;
            selectList.appendChild(option);
        }
    }

    loadCouses();

    function sendFeedback() {
        var course = document.getElementById("coursesSelect").value;
        var labs = document.getElementById("labsSelect").value;
        var comment = document.getElementById("comment").value;

        $.ajax({
            url: 'courses?action=write&course=' + course + '&labs=' + labs + '&comment=' + comment
        }).done(function (response) {
            location.href = "index.jsp";
        });
    }

</script>
</html>