package com.tabarak.useraccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tabarak.useraccess.utils.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  
	private static final long serialVersionUID = -3092147103268450245L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                // Set up the user session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                // Redirect based on user role
                switch (role) {
                    case "Employee":
                        response.sendRedirect("requestAccess.jsp");
                        break;
                    case "Manager":
                        response.sendRedirect("pendingRequests.jsp");
                        break;
                    case "Admin":
                        response.sendRedirect("createSoftware.jsp");
                        break;
                    default:
                        response.sendRedirect("login.jsp");
                        break;
                }
            } else {
                // Invalid login, redirect back to login page with error
                response.sendRedirect("login.jsp?error=Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error during login.");
        }
    }
}
