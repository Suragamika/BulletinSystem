package com.classbulletin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    // 数据库 URL、用户名、密码
    private static final String URL = "jdbc:mysql://localhost:3306/class_bulletin?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER = "root"; // 替换为你的数据库用户名
    private static final String PASSWORD = "Xiaoxiao234"; // 替换为你的数据库密码

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        try {
            // 加载 MySQL 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("数据库驱动加载失败！");
        }
    }
}
