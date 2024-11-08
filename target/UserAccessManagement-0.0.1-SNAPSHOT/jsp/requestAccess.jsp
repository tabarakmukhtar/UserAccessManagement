<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Request Access</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script>
        function validateRequestForm() {
            const reason = document.getElementById("reason").value;

            if (reason.trim() === "") {
                alert("Please provide a reason for your access request.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h2>Request Software Access</h2>
    <form action="/RequestServlet" method="post" onsubmit="return validateRequestForm()">
        <label for="software">Software:</label>
        <select id="software" name="software_id" required>
    <%= request.getAttribute("softwareOptions") != null ? request.getAttribute("softwareOptions") : "" %>
</select><br><br>

        <label for="accessType">Access Type:</label>
        <select id="accessType" name="access_type" required>
            <option value="Read">Read</option>
            <option value="Write">Write</option>
            <option value="Admin">Admin</option>
        </select><br><br>

        <label for="reason">Reason for Request:</label><br>
        <textarea id="reason" name="reason" rows="4" cols="50" required></textarea><br><br>

        <input type="submit" value="Submit Request">
    </form>

    <% if (request.getParameter("success") != null) { %>
        <p class="success">Access request submitted successfully!</p>
    <% } %>

    <p><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></p>

</body>
</html>
