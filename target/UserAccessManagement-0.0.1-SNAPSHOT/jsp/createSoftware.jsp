<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Software</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script>
        function confirmSoftwareCreation() {
            return confirm("Are you sure you want to create this software?");
        }
    </script>
</head>
<body>
    <h2>Create New Software</h2>
    <form action="/SoftwareServlet" method="post" onsubmit="return confirmSoftwareCreation()">
        <label for="name">Software Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="4" cols="50" required></textarea><br><br>

        <label>Access Levels:</label><br>
        <input type="checkbox" name="access_levels" value="Read"> Read<br>
        <input type="checkbox" name="access_levels" value="Write"> Write<br>
        <input type="checkbox" name="access_levels" value="Admin"> Admin<br><br>

        <input type="submit" value="Create Software">
    </form>
<% 
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "login.jsp");
        return;
    }
%>

    <% if (request.getParameter("success") != null) { %>
        <p class="success">Software successfully created!</p>
    <% } %>
   <p><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></p>
   
</body>
</html>
