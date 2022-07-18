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
        String getUserByEmailQuery = "select userIdx, name, nickName, email from User where email=?";
        String getUserByEmailParams = Email;
        return this.jdbcTemplate.queryForObject(getUserByEmailQuery, 
        (rs, rowNum)-> new GetUserRes(
            rs.getInt("userIdx"), 
            rs.getString("name"), 
            rs.getString("nickName"), 
            rs.getString("email"))
            , getUserByEmailParams);

    }

    public GetUserRes getUserByIdx(int userIdx){
        String getUserByIdxQuery = "select userIdx,name,nickName,email from User where userIdx=?";
        int getUserByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUserByIdxParams);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (name, nickName, email, password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(), postUserReq.getEmail(), postUserReq.getPassword()};
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

    public int checkNickNameExist(String nickName){
        String checkNickNameExistQuery = "select exists(select nickName from User where nickName = ?)";
        String checkNickNameExistParams = nickName;
        return this.jdbcTemplate.queryForObject(checkNickNameExistQuery,
                int.class,
                checkNickNameExistParams);

    }
}
