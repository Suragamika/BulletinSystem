package com.classbulletin.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.classbulletin.util.DBUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/recover-password")
public class RecoverPasswordServlet extends HttpServlet {
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
            String newPassword = jsonObject.getString("newPassword");

            // 检查输入
            if (username == null || username.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
                response.getWriter().write("{\"success\": false, \"message\": \"用户名或新密码不能为空！\"}");
                return;
            }

            // 更新密码
            try (Connection conn = DBUtils.getConnection()) {
                String sql = "UPDATE User SET password = ? WHERE username = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, newPassword);
                    stmt.setString(2, username);
                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        response.getWriter().write("{\"success\": true, \"message\": \"密码更新成功！\"}");
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"用户名不存在！\"}");
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

