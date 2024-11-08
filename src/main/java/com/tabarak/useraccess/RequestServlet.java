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

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and has role 'Employee'
        if (username == null || !"Employee".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Collect request data
        int softwareId = Integer.parseInt(request.getParameter("software_id"));
        String accessType = request.getParameter("access_type");
        String reason = request.getParameter("reason");

        try (Connection conn = Database.getConnection()) {
            // Get user ID based on username
            String userQuery = "SELECT id FROM users WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(userQuery);
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();
            int userId = userRs.next() ? userRs.getInt("id") : -1;

            // Insert request into 'requests' table
            String sql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, softwareId);
            stmt.setString(3, accessType);
            stmt.setString(4, reason);
            stmt.executeUpdate();

            // Redirect to confirmation or success page
            response.sendRedirect("requestAccess.jsp?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error while submitting access request.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Populate software list for requestAccess.jsp
        response.setContentType("text/html");
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT id, name FROM software";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder options = new StringBuilder();
            while (rs.next()) {
                options.append("<option value=\"").append(rs.getInt("id")).append("\">")
                       .append(rs.getString("name")).append("</option>");
            }
            request.setAttribute("softwareOptions", options.toString());
            request.getRequestDispatcher("requestAccess.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

