package com.tabarak.useraccess;

import com.tabarak.useraccess.utils.Database;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        // Only managers can access this page
        if (!"Manager".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Generate HTML rows for each pending request
        StringBuilder requestRows = new StringBuilder();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT r.id, u.username AS employee_name, s.name AS software_name, " +
                         "r.access_type, r.reason FROM requests r " +
                         "JOIN users u ON r.user_id = u.id " +
                         "JOIN software s ON r.software_id = s.id " +
                         "WHERE r.status = 'Pending'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int requestId = rs.getInt("id");
                String employeeName = rs.getString("employee_name");
                String softwareName = rs.getString("software_name");
                String accessType = rs.getString("access_type");
                String reason = rs.getString("reason");

                // Generate HTML row with Approve and Reject forms
                requestRows.append("<tr>")
                    .append("<td>").append(employeeName).append("</td>")
                    .append("<td>").append(softwareName).append("</td>")
                    .append("<td>").append(accessType).append("</td>")
                    .append("<td>").append(reason).append("</td>")
                    .append("<td>")
                    .append("<form action='../ApprovalServlet' method='post' style='display:inline;' onsubmit='return confirmAction(\"approve\")'>")
                    .append("<input type='hidden' name='request_id' value='").append(requestId).append("'>")
                    .append("<input type='submit' name='action' value='Approve'>")
                    .append("</form>")
                    .append("<form action='../ApprovalServlet' method='post' style='display:inline;' onsubmit='return confirmAction(\"reject\")'>")
                    .append("<input type='hidden' name='request_id' value='").append(requestId).append("'>")
                    .append("<input type='submit' name='action' value='Reject'>")
                    .append("</form>")
                    .append("</td>")
                    .append("</tr>");
            }
            request.setAttribute("requestRows", requestRows.toString());
            request.getRequestDispatcher("pendingRequests.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        // Only managers can approve/reject requests
        if (!"Manager".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        int requestId = Integer.parseInt(request.getParameter("request_id"));
        String action = request.getParameter("action");

        // Update request status based on the action
        String newStatus = "Approve".equals(action) ? "Approved" : "Rejected";

        try (Connection conn = Database.getConnection()) {
            String sql = "UPDATE requests SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);
            stmt.executeUpdate();

            // Redirect back to the pending requests page
            response.sendRedirect("/ApprovalServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error processing request.");
        }
    }
}

