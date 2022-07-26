package com.exerciseday.dev.src.user;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.user.model.*;

import javax.sql.DataSource;

import java.util.List;


@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*
    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select userIdx,name, nickName, email from User";
        return this.jdbcTemplate.query(getUsersQuery,
        (rs, rowNum) -> new GetUserRes(
            rs.getInt("userIdx"),
            rs.getString("name"),
            rs.getString("nickName"), 
            rs.getString("email"))
        );
    }
    */
    public GetUserRes getUserByEmail(String Email){
        String getUserByEmailQuery = "select userIdx,email,nickname,phone from User where email=?";
        String getUserByEmailParams = Email;
        return this.jdbcTemplate.queryForObject(getUserByEmailQuery, 
        (rs, rowNum)-> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("phone")),
                        getUserByEmailParams);

    }

    public GetUserRes getUserByIdx(int userIdx){
        String getUserByIdxQuery = "select userIdx,email,nickname,phone from User where userIdx=?";
        int getUserByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("phone")),
                        getUserByIdxParams);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (email, password, nickname, phone ) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickname(), postUserReq.getPhone()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNicknameExist(String nickname){
        String checkNicknameExistQuery = "select exists(select nickname from User where nickname = ?)";
        String checkNicknameExistParams = nickname;
        return this.jdbcTemplate.queryForObject(checkNicknameExistQuery,
                int.class,
                checkNicknameExistParams);

    }

    public int checkPhoneExist(String phone){
        String checkPhoneExistQuery = "select exists(select phone from User where phone = ?)";
        String checkPhoneExistParams = phone;
        return this.jdbcTemplate.queryForObject(checkPhoneExistQuery,
                int.class,
                checkPhoneExistParams);

    }
}
