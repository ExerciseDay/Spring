package com.exerciseday.dev.src.auth;

import java.util.List;

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
        String getUserByLoginReqQuery = "SELECT userIdx, userEmail, userPwd, userNickname, userTel,userGender, userImg, userGoal, userCreate FROM User WHERE userEmail = ?";
        String getUserByLoginReqParams = postLoginReq.getEmail();
        
        return this.jdbcTemplate.queryForObject(getUserByLoginReqQuery,
                (rs, rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userPwd"),
                        rs.getString("userNickname"),
                        rs.getString("userTel"),
                        rs.getString("userGender"),
                        rs.getString("userImg"),
                        rs.getString("userGoal"),
                        rs.getString("userCreate")
                        
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
    public int checkUserLogin(int userIdx){
        String checkUserLoginQuery = "select exists(select userIdx from User where userIdx = ? AND userStatus = 'ACTIVE')";
        int checkUserLoginParam = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserLoginQuery,int.class, checkUserLoginParam);
    }

    public int checkUserLogout(int userIdx){
        String checkUserLoginQuery = "select exists(select userIdx from User where userIdx = ? AND userStatus = 'INACTIVE')";
        int checkUserLoginParam = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserLoginQuery,int.class, checkUserLoginParam);
    }

    public int checkUserDelete(int userIdx){
        String checkUserLoginQuery = "select exists(select userIdx from User where userIdx = ? AND userStatus = 'DELETE')";
        int checkUserLoginParam = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserLoginQuery,int.class, checkUserLoginParam);
    }
    public List<GetTagRes> getRamdomTags(){
        String getRamdomTagsQuery = "SELECT tagIdx, tagName FROM Tag ORDER BY RAND() LIMIT 4";
        //List<GetTagExpertInfoRes> expertInfos;
        return this.jdbcTemplate.query(getRamdomTagsQuery,
                                             (rs, rowNum) -> new GetTagRes(
                                                        rs.getInt("tagIdx"), 
                                                        rs.getString("tagName"), 
                                                        this.jdbcTemplate.query(
                                                            "SELECT e.eCourseIdx, e.eCourseName, e.eCourseImg\n"+
                                                            "FROM ExpertCourse as e\n"+
                                                            "   join ExpertCourse_has_Tag as et on et.ExpertCourse_eCourseIdx = e.eCourseIdx\n"+
                                                            "   join Tag as t on t.tagIdx = et.Tag_tagIdx\n"+
                                                            "WHERE t.tagIdx = ?\n"+
                                                            "LIMIT 4",
                                                            (rk,rownum)->new GetTagExpertInfoRes(
                                                                rk.getInt("e.eCourseIdx"),
                                                                rk.getString("e.eCourseName"),
                                                                rk.getString("e.eCourseImg")
                                                            ),
                                                            rs.getInt("tagIdx")
                                                                    )
                                                            )
                                        );
    }
}
 