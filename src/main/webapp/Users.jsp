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
    <title>Users</title>
    <link href="Css/Users.css" rel="stylesheet" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div id="header">
    <div id="logo">
        <img src="https://www.fasttrackit.org/wp-content/uploads/2017/08/Fastrackit-1.png">
    </div>
    <h1>Users</h1>
</div>

<div id="menu">
    <ul>
        <li><a href="Feedback.jsp">Feedback</a></li>
        <li><a href="Users.jsp">Users</a></li>
    </ul>
</div>

<div id="content">

    <div id="addUser">
        <div id="usernameField">
            <p>Username</p>
            <input type="text" id="username" placeholder="Username"/>
        </div>

        <div id="passwordField">
            <p>Password</p>
            <input type="text" id="password" placeholder="Password"/>
        </div>

        <div id="userType">
            <p>Select type</p>
            <select id="type">
                <option value="user">User</option>
                <option value="admin">Admin</option>
            </select>
        </div>

        <div id="buttonField">
            <input type="button" id="add" value="Add User" onClick="add()"/>
        </div>
    </div>

    <div id="listOfUsers">
        <table>
            <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>User type</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody id="users">

            </tbody>
        </table>
    </div>
</div>


</body>
<script>

    function add() {
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        var userType = document.getElementById('type').value;

        $.ajax({
            url: 'users?action=add&username=' + username + '&password=' + password + '&usernameType=' + userType
        }).done(function (response) {
            location.href = "Users.jsp";
        });
    }

    function loadUsers() {
        $.ajax({
            url: 'users?action=read'
        }).done(function (response) {
            putFeedbackInHTML(response.users);
        });
    }

    function putFeedbackInHTML(users) {
        var divCourses = document.getElementById('users');
        var list = '';

        for (var i = 0; i < users.length; i++) {
            var id = users[i].id;
            var username = users[i].username;
            var password = users[i].password;
            var type = users[i].type;
            var user =
                '<tr>' +
                '<td>' + username + '</td>' +
                '<td>' + password + '</td>' +
                '<td>' + type + '</td>' +
                '<td>' + '<button class="deleteButton" onclick="deleteUser(' + id + ')">Delete</button>' + '</td>' +
                '</tr>'


            list = list + user;
        }

        divCourses.innerHTML = list;
        $(users)
    }

    loadUsers();

    function deleteUser(id) {
        $.ajax({
            url: 'users?action=delete&id=' + id
        }).done(function (response) {
            location.href = "Users.jsp";
        });
    }
</script>

</html>


