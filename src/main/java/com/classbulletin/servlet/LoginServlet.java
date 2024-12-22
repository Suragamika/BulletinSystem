package com.classbulletin.servlet;

import org.json.JSONObject;

import com.classbulletin.util.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // 获取前端发送的 JSON 数据
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        try {
            JSONObject jsonObject = new JSONObject(json.toString());
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");

            // 打印日志
            System.out.println("收到登录请求: username=" + username + ", password=" + password);

            // 检查用户名和密码
            try (Connection conn = DBUtils.getConnection()) {
                String sql = "SELECT password, role FROM User WHERE username = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String storedPassword = rs.getString("password");
                            String role = rs.getString("role");

                            if (storedPassword.equals(password)) {
                                // 登录成功，返回角色信息
                                JSONObject responseJson = new JSONObject();
                                responseJson.put("success", true);
                                responseJson.put("message", "登录成功！");
                                responseJson.put("role", role); // 返回角色

                                response.getWriter().write(responseJson.toString());
                            } else {
                                // 密码错误
                                response.getWriter().write("{\"success\": false, \"message\": \"密码错误！\"}");
                            }
                        } else {
                            // 用户名不存在
                            response.getWriter().write("{\"success\": false, \"message\": \"用户名不存在！\"}");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("{\"success\": false, \"message\": \"数据库错误！\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"请求数据格式错误！\"}");
        }
    }
}
