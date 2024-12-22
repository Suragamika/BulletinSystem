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

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
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

            // 检查输入
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                response.getWriter().write("{\"success\": false, \"message\": \"用户名或密码不能为空！\"}");
                return;
            }

            // 检查是否尝试注册管理员
            if (username.equals("lgl")) {
                response.getWriter().write("{\"success\": false, \"message\": \"禁止注册管理员账号！\"}");
                return;
            }

            // 检查用户名是否已存在
            try (Connection conn = DBUtils.getConnection()) {
                String checkUserSql = "SELECT * FROM User WHERE username = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {
                    checkStmt.setString(1, username);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            response.getWriter().write("{\"success\": false, \"message\": \"用户名已存在！\"}");
                            return;
                        }
                    }
                }

                // 插入新用户（默认角色为 user）
                String insertUserSql = "INSERT INTO User (username, password, role) VALUES (?, ?, 'user')";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertUserSql)) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    int rowsInserted = insertStmt.executeUpdate();

                    if (rowsInserted > 0) {
                        response.getWriter().write("{\"success\": true, \"message\": \"注册成功！\"}");
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"注册失败，请重试！\"}");
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
