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
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
     * public List<GetUserRes> getUsers(){
     * String getUsersQuery = "select userIdx,name, nickName, email from User";
     * return this.jdbcTemplate.query(getUsersQuery,
     * (rs, rowNum) -> new GetUserRes(
     * rs.getInt("userIdx"),
     * rs.getString("name"),
     * rs.getString("nickName"),
     * rs.getString("email"))
     * );
     * }
     */
    public GetUserRes getUserByEmail(String Email) {
        String getUserByEmailQuery = "select userIdx,userEmail,userNickname,userTel from User where userEmail=?";
        String getUserByEmailParams = Email;
        return this.jdbcTemplate.queryForObject(getUserByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userTel")),
                getUserByEmailParams);

    }

    public User getUser(int userIdx) {
        String getUserByIdxQuery = "select userIdx,userEmail, userPwd, userNickname,userTel from User where userIdx=?";
        int getUserByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserByIdxQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userPwd"),
                        rs.getString("userNickname"),
                        rs.getString("userTel")),
                getUserByIdxParams);
    }

    public GetUserRes getUserByIdx(int userIdx) {
        String getUserByIdxQuery = "select userIdx,userEmail,userNickname,userTel from User where userIdx=?";
        int getUserByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userTel")),
                getUserByIdxParams);
    }

    // return type GetUserRes - 핸드폰으로 유저 찾기
    public GetUserRes getUserByPhone(String phone) {
        String getUserByPhoneQuery = "select userIdx,userEmail,userNickname,userTel from User where userTel=?";
        String getUserByPhoneParams = phone;
        return this.jdbcTemplate.queryForObject(getUserByPhoneQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userTel")),
                getUserByPhoneParams);
    }

    // return type User - 핸드폰으로 유저 찾기
    public User getUserByPhoneTU(String phone) {
        String getUserByPhoneQuery = "select userIdx,userEmail,userNickname,userTel from User where userTel=?";
        String getUserByPhoneParams = phone;
        return this.jdbcTemplate.queryForObject(getUserByPhoneQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userTel"), getUserByPhoneParams),
                getUserByPhoneParams);
    }

    public GetUserFindEmailRes getUserFindEmail(String phone) {
        String getUserFindEmailQuery = "select userIdx, userEmail, userCreate, userImg from User where userTel=?";
        String getUserFindEmailParams = phone;
        return this.jdbcTemplate.queryForObject(getUserFindEmailQuery,
                (rs, rowNum) -> new GetUserFindEmailRes(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getDate("userCreate"),
                        rs.getString("userImg")),
                getUserFindEmailParams);
    }

    public int postUserFindPwd(String phone) {
        String postUserFindPwdQuery = "select userIdx from User where userTel=?";
        String postUserFindPwdParams = phone;
        return this.jdbcTemplate.queryForObject(postUserFindPwdQuery,
                (rs, rowNum) -> rs.getInt("userIdx"),
                postUserFindPwdParams);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (userEmail, userPwd, userNickname, userTel ) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[] { postUserReq.getEmail(), postUserReq.getPassword(),
                postUserReq.getNickname(), postUserReq.getPhone() };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNicknameExist(String nickname) {
        String checkNicknameExistQuery = "select exists(select userNickname from User where userNickname = ?)";
        String checkNicknameExistParams = nickname;
        return this.jdbcTemplate.queryForObject(checkNicknameExistQuery,
                int.class,
                checkNicknameExistParams);

    }

    public int checkPhoneExist(String phone) {
        String checkPhoneExistQuery = "select exists(select userTel from User where userTel = ?)";
        String checkPhoneExistParams = phone;
        return this.jdbcTemplate.queryForObject(checkPhoneExistQuery,
                int.class,
                checkPhoneExistParams);

    }

    public int editPwd(PatchUserEditPwdReq patchUserEditPwdReq) {
        String patchUserEditPwdQuery = "update User set userPwd = ? where userIdx = ? ";
        Object[] patchUserEditPwdParams = new Object[] { patchUserEditPwdReq.getPassword(),
                patchUserEditPwdReq.getUserIdx() };

        return this.jdbcTemplate.update(patchUserEditPwdQuery, patchUserEditPwdParams);
    }

    public int editNickname(PatchUserEditNicknameReq patchUserEditNicknameReq) {
        String patchUserEditNicknameQuery = "update User set userNickname = ? where userIdx = ? ";
        Object[] patchUserEditNicknameParams = new Object[] { patchUserEditNicknameReq.getNickname(),
                patchUserEditNicknameReq.getUserIdx() };

        return this.jdbcTemplate.update(patchUserEditNicknameQuery, patchUserEditNicknameParams);
    }

    public int editImg(PatchUserEditImgReq patchUserEditImgReq) {
        String patchUserEditImgQuery = "update User set userImg = ? where userIdx = ? ";
        Object[] patchUserEditImgParams = new Object[] { patchUserEditImgReq.getImg(),
                patchUserEditImgReq.getUserIdx() };

        return this.jdbcTemplate.update(patchUserEditImgQuery, patchUserEditImgParams);
    }

    public int deleteUser(int userIdx) {
        String deleteUserQuery = "update User set userStatus = 0 where userIdx = ? ";
        Object[] deleteUserParams = new Object[] { userIdx };

        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }
}
