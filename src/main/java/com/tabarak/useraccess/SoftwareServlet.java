package com.tabarak.useraccess;

import com.tabarak.useraccess.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Collect selected access levels
        String[] accessLevelsArray = request.getParameterValues("access_levels");
        String accessLevels = String.join(", ", accessLevelsArray != null ? accessLevelsArray : new String[0]);

        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, accessLevels);
            stmt.executeUpdate();

            // Redirect back to the create software page or to a success page
            response.sendRedirect("createSoftware.jsp?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error while adding software.");
        }
    }
}
