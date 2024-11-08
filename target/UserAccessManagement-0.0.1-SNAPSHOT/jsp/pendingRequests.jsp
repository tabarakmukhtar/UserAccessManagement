<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Pending Access Requests</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script>
        function confirmAction(action) {
            return confirm(`Are you sure you want to ${action} this request?`);
        }
    </script>
</head>
<body>
    <h2>Pending Access Requests</h2>
   <table border="1">
    <tr>
        <th>Employee Name</th>
        <th>Software Name</th>
        <th>Access Type</th>
        <th>Reason</th>
        <th>Actions</th>
    </tr>
    <%= request.getAttribute("requestRows") != null ? request.getAttribute("requestRows") : "" %>
</table>

   <p><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></p>

</body>
</html>
