package com.asapp.AuthenticationService.AuthSvc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.asapp.AuthenticationService.AuthService.AuthResponse;
import com.asapp.AuthenticationService.AuthService.LoginResponse;
import com.asapp.UserService.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class AuthDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;
    }
    public Optional<AuthResponse> createUser(String userName, String password) {

        String encodedPassword = calculateSHA256(password);
        if (encodedPassword == null) {
            return Optional.empty();
        }
        String insertSql =
            "INSERT INTO user (" +
                " user_name, " +
                " password, " +
                " created_at, " +
                " last_login) " +
                "VALUES (?, ?, ?, ?)";

        Object[] params = new Object[] { userName, encodedPassword, new Date(), new Date() };

        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP};
        try{
            int row = this.jdbcTemplate.update(insertSql,params,types);
            if(row!=1){
                AuthResponse authResponse = new AuthResponse(ErrorResponse.UNABLE_TO_SIGN_UP);
                return Optional.of(authResponse);
            }
        } catch (Exception e){
            AuthResponse authResponse = new AuthResponse(ErrorResponse.UNABLE_TO_SIGN_UP);
            return Optional.of(authResponse);
        }
        AuthResponse authResponse = new AuthResponse(userName);
        return Optional.of(authResponse);
    }


    public Optional<LoginResponse> login(String userName, String password){
        String selectSql =  "SELECT * FROM user where user_name=? AND password=?";
        try{
            User user = this.jdbcTemplate.queryForObject(selectSql,new Object[] {userName,calculateSHA256(password)}, new UserMapper());
            if (user!=null) {
                String authToken = generateAuthToken();
                LoginResponse loginResponse = new LoginResponse(user.getUserId(), userName, authToken);
                saveAuthToken(user.getUserId(),authToken);
                return Optional.of(loginResponse);
            }
        } catch (Exception e){
            LoginResponse loginResponse = new LoginResponse(ErrorResponse.UNABLE_TO_LOGIN);
            return Optional.of(loginResponse);
        }
        return Optional.empty();
    }

    public void saveAuthToken(int userId, String authToken) {
        String sql;
        Object[] params;
        int[] types;
        if(loggedInBefore(userId)) {
            sql = "UPDATE user_token SET auth_token = ? , status = ? where user_id = ?";
            params = new Object[] { authToken, 1, userId};
            types = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER };
        } else {
            sql = "INSERT INTO user_token (" + " user_id, " + " auth_token, " + " status) " + "VALUES (?, ?, ?)";
            params = new Object[] { userId, authToken, 1};
            types = new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER };
        }
        try{
            int row = jdbcTemplate.update(sql, params, types);
        } catch (Exception e) {
            System.out.println("Exception "+ e);
        }
    }

    private boolean loggedInBefore(int userId) {
        String selectSql =  "SELECT user_id FROM user_token where user_id=?";
        try{
            int row = this.jdbcTemplate.queryForObject(selectSql,new Object[] {userId}, Integer.class);
            return row == userId;
        } catch (Exception e) {
            return false;
        }
    }


    private String generateAuthToken() {
        String randToken = generateRandomToken();
        String encodedToken = calculateSHA256(randToken);
        return encodedToken;
    }

    public String generateRandomToken() {
        return UUID.randomUUID().toString();
    }

    private String calculateSHA256(String unhashedString) {
        if (unhashedString == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(unhashedString.getBytes(StandardCharsets.UTF_8));
            String encodedToken = Base64.getEncoder().encodeToString(hash);
            return encodedToken;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public boolean isValidToken(String authToken) {
        String selectSql =  "SELECT status FROM user_token where auth_token=?";
        try{
            String status = this.jdbcTemplate.queryForObject(selectSql,new Object[] {authToken}, String.class);
            return status.equals("1");
        } catch (Exception e) {
            return false;
        }
    }
}
