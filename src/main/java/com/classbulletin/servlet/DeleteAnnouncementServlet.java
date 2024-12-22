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

@WebServlet("/api/announcements/delete/*")
public class DeleteAnnouncementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 数据库配置信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/class_bulletin?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";


    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Xiaoxiao234";

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json; charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");


        // 获取路径参数
        String pathInfo = request.getPathInfo(); // e.g., "/1"
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"公告ID无效\"}");
            return;
        }

        String idStr = pathInfo.substring(1); // 去掉前导斜杠
        int id;
        try {
            id = Integer.parseInt(idStr); // 检查 ID 是否为数字
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"公告ID必须是数字\"}");
            return;
        }

        // 删除公告
        try {
            // 加载 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用 try-with-resources 自动关闭资源
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Announcement WHERE id = ?")) {

                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.getWriter().write("{\"success\": true, \"message\": \"公告删除成功\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"success\": false, \"message\": \"公告未找到\"}");
                }
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误：JDBC 驱动未找到\"}");
            e.printStackTrace(); // 可以替换为日志记录
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误，请稍后再试\"}");
            e.printStackTrace(); // 可以替换为日志记录
        }
    }
}
