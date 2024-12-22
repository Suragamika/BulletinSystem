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

@WebServlet("/api/announcements/edit/*")
public class EditAnnouncementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 数据库配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/class_bulletin?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";


    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Xiaoxiao234";

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json; charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8"); // 确保请求也使用 UTF-8 编码


        // 解析路径参数
        String pathInfo = request.getPathInfo(); // e.g., "/1"
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"公告ID无效\"}");
            return;
        }

        String idStr = pathInfo.substring(1); // 去掉前导斜杠
        int id;
        try {
            id = Integer.parseInt(idStr); // 转换为整数
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"公告ID必须是数字\"}");
            return;
        }

        // 解析请求体
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
            response.getWriter().write("{\"message\":\"无效的请求体\"}");
            return;
        }

        // 获取并验证输入
        String title = requestBody.optString("title");
        String content = requestBody.optString("content");
        if (title.isEmpty() || content.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"标题和内容不能为空\"}");
            return;
        }

        // 更新数据库
        try {
            // 加载 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用 try-with-resources 自动管理资源
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("UPDATE Announcement SET title = ?, content = ? WHERE id = ?")) {

                stmt.setString(1, title); // 设置标题
                stmt.setString(2, content); // 设置内容
                stmt.setInt(3, id); // 设置 ID

                int rowsAffected = stmt.executeUpdate(); // 执行更新
                if (rowsAffected > 0) {
                    response.getWriter().write("{\"message\":\"公告更新成功\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\":\"公告未找到\"}");
                }
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"服务器错误：JDBC 驱动未找到\"}");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"服务器错误，请稍后再试\"}");
            e.printStackTrace();
        }
    }
}

