package com.classbulletin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/api/announcements/create")
public class CreateAnnouncementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/class_bulletin?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Xiaoxiao234";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json; charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8"); // 确保请求也使用 UTF-8 编码

        JSONObject requestBody;
        try {
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                bodyBuilder.append(line);
            }
            requestBody = new JSONObject(bodyBuilder.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"无效的请求体\"}");
            return;
        }

        String title = requestBody.optString("title");
        String content = requestBody.optString("content");

        if (title.isEmpty() || content.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"标题和内容不能为空\"}");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Announcement (title, content) VALUES (?, ?)")) {

                stmt.setString(1, title);
                stmt.setString(2, content);
                stmt.executeUpdate();

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"success\": true, \"message\": \"公告创建成功\"}");
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误：JDBC 驱动未找到\"}");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误，请稍后再试\"}");
            e.printStackTrace();
        }
    }
}
