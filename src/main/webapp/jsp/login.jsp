<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script>
        function validateLoginForm() {
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
    <h2>Login</h2>
    <form action="/LoginServlet" method="post" onsubmit="return validateLoginForm()">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Login">
    </form>
    <p>Don't have an account? <a href="signup.jsp">Sign up here</a>.</p>

    <% if (request.getParameter("error") != null) { %>
        <p class="error">Invalid credentials. Please try again.</p>
    <% } %>
</body>
</html>
