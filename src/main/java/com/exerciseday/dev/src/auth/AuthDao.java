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
        String getUserByLoginReqQuery = "SELECT userIdx, userEmail, userPwd, userNickname, userTel,userImg FROM User WHERE userEmail = ?";
        String getUserByLoginReqParams = postLoginReq.getEmail();
        
        return this.jdbcTemplate.queryForObject(getUserByLoginReqQuery,
                (rs, rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userPwd"),
                        rs.getString("userNickname"),
                        rs.getString("userTel"),
                        rs.getString("userImg")
                        
                ), getUserByLoginReqParams);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    public int getUserIdxByJWT(int userIdxByJwt){
        String getUserIdxByJWTQuery = "SELECT userIdx FROM User WHERE userIdx = ?";
        int getUserIdxByJWTParams = userIdxByJwt;
        
        return this.jdbcTemplate.queryForObject(getUserIdxByJWTQuery,
                (rs, rowNum)-> rs.getInt("userIdx")
                , getUserIdxByJWTParams);
    }
}
