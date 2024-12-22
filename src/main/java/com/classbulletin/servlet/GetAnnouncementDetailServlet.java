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

import org.json.JSONObject;

@WebServlet("/api/announcements/*")
public class GetAnnouncementDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo(); // 获取路径参数
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 获取 ID，假设路径为 /api/announcements/{id}
        String idStr = pathInfo.substring(1); // 去掉前导斜杠

        // 尝试将 ID 转换为整数
        int id;
        try {
            id = Integer.parseInt(idStr); 
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"无效的公告ID\"}");
            return;
        }

        JSONObject announcement = new JSONObject();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/class_bulletin", "root", "Xiaoxiao234");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Announcement WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 如果找到公告，返回数据
                announcement.put("id", rs.getInt("id"));
                announcement.put("title", rs.getString("title"));
                announcement.put("content", rs.getString("content"));
                announcement.put("createdAt", rs.getString("created_at"));
            } else {
                // 如果没有找到，返回 404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"服务器错误，请稍后再试\"}");
            return;
        }

        // 输出公告内容
        try (PrintWriter out = response.getWriter()) {
            out.print(announcement.toString());
            out.flush();
        }
    }
}
