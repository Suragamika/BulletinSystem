package com.classbulletin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/announcements")
public class GetAnnouncementsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/class_bulletin?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Xiaoxiao234";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray announcements = new JSONArray();

        try {
            Class.forName(JDBC_DRIVER);

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("SELECT id, title, content, created_at FROM Announcement");
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    JSONObject announcement = new JSONObject();
                    announcement.put("id", rs.getInt("id"));
                    announcement.put("title", rs.getString("title"));
                    announcement.put("content", rs.getString("content")); // 添加 content 字段
                    announcement.put("createdAt", rs.getString("created_at"));
                    announcements.put(announcement);
                }
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"JDBC 驱动加载失败，请检查配置！\"}");
            e.printStackTrace();
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"服务器错误，请稍后再试！\"}");
            e.printStackTrace();
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(announcements.toString());
            out.flush();
        }
    }
}
