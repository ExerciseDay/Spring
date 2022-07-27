package com.exerciseday.dev.src.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.auth.model.*;

@Repository
public class AuthDao {
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUserByLoginReq(PostLoginReq postLoginReq){
        String getUserByLoginReqQuery = "SELECT userIdx, email, password, nickname, phone FROM User WHERE email = ?";
        String getUserByLoginReqParams = postLoginReq.getEmail();
        
                
        return this.jdbcTemplate.queryForObject(getUserByLoginReqQuery,
                (rs, rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("phone")
                        
                ), getUserByLoginReqParams);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }
}
