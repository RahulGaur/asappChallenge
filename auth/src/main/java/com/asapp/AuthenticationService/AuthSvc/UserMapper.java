package com.asapp.AuthenticationService.AuthSvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.asapp.UserService.User;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(convertToDate(rs.getString("created_at")));
        user.setLastLogin(convertToDate(rs.getString("last_login")));
        return user;
    }

    private Date convertToDate(String stringTime) {
        return new Date(Long.parseLong(stringTime));
    }
}
