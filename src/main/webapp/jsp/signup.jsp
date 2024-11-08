<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script>
        function validateSignUpForm() {
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            if (username === "" || password === "") {
                alert("Please fill in both username and password.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h2>Sign Up</h2>
    <form action="<%= request.getContextPath() %>/SignUpServlet" method="post" onsubmit="return validateSignUpForm()">

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Sign Up">
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a>.</p>
     <% if (request.getParameter("error") != null) { %>
    <p class="error"><%= request.getParameter("error") %></p>
    <% } %>

    <% if (request.getParameter("success") != null) { %>
        <p class="success">Account successfully created! Please log in.</p>
    <% } %>
</body>
</html>
