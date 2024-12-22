package com.classbulletin.dao;

import com.classbulletin.dto.User;
import com.classbulletin.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCheck {
    // 验证用户是否存在
    public boolean validate(User user) {
        boolean valid = false;
        try (Connection conn = DBUtils.getConnection()) {
            String sql = "SELECT * FROM User WHERE username=? AND password=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    valid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valid;
    }
}
