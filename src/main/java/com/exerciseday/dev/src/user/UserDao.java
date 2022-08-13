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
        String getUserByEmailQuery = "select userIdx,userEmail,userNickname,userTel from User where userEmail=?";
        String getUserByEmailParams = Email;
        return this.jdbcTemplate.queryForObject(getUserByEmailQuery, 
        (rs, rowNum)-> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userTel")),
                        getUserByEmailParams);

    }

    public User getUser(int userIdx){
        String getUserByIdxQuery = "select userIdx,userEmail,userPwd, userNickname, userTel, userImg, userGoal from User where userIdx=?";
        int getUserByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserByIdxQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),                        
                        rs.getString("userPwd"),
                        rs.getString("userNickname"),                        
                        rs.getString("userTel"),
                        rs.getString("userImg"),
                        rs.getString("userGoal")),
                        getUserByIdxParams);
    }


    public GetUserRes getUserByIdx(int userIdx){
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

    public GetUserRes getUserByPhone(String phone){
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


    public GetUserFindEmailRes getUserFindEmail(String phone){
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
    
    public List<GetUserCustomRes> getUserCustoms(int userIdx){
        String getUserCustomsQuery = ""+
                "SELECT c.cCourseIdx, \n"+
                "       c.cCourseName, \n"+
                "       c.cCourseTime, \n"+
                "       c.cCourseCalory \n"+
                "FROM   CustomCourse as c\n"+
                "    join User as u on u.userIdx = c.User_userIdx\n"+
                "WHERE u.userIdx = ?";             
        int getUserCustomsParam = userIdx;
        return this.jdbcTemplate.query(getUserCustomsQuery,
                (rs,rowNum) -> new GetUserCustomRes(
                    rs.getInt("c.cCourseIdx"),
                    rs.getString("c.cCourseName"),
                    rs.getInt("c.cCourseTime"),
                    rs.getInt("c.cCourseCalory")
                ),
                getUserCustomsParam);
    }
    /*
    public List<GetUserExpertRes> getUserExperts(int userIdx){
        String getUserExpertsQuery = ""+
                "SELECT e.eCourseIdx, \n"+
                "       e.eCourseName, \n"+
                "       e.eCourseTime, \n"+
                "       e.eCourseCalory \n"+
                "FROM   ExpertCourse as e\n"+
                "    join User as u on u.userIdx = e.User_userIdx\n"+
                "WHERE u.userIdx = ?";              
        int getUserExpertsParam = userIdx;
        return this.jdbcTemplate.query(getUserExpertsQuery,
                (rs,rowNum) -> new GetUserExpertRes(
                    rs.getInt("e.eCourseIdx"),
                    rs.getString("e.eCourseName"),
                    rs.getInt("e.eCourseTime"),
                    rs.getInt("e.eCourseCalory")
                ),
                getUserExpertsParam);
    }
    */


    // 유저 - 전문가 코스 테이블 연결 테이블
    public List<Integer> getRelation(int userIdx){
        String getRelationQuery = "SELECT ue.ExpertCourse_eCourseIdx\n"+
        "                          FROM User_has_ExpertCourse as ue"+
        "                                 join User as u on u.userIdx = ue.User_userIdx"+
        "                           WHERE u.userIdx = ?";
        int getRelationParam = userIdx;
        return this.jdbcTemplate.queryForList(getRelationQuery,
                                        Integer.class,
                                        getRelationParam);


    }

    public GetUserExpertRes getUserExperts(int expertIdx){
        String getUserExpertsQuery = "SELECT e.eCourseIdx, e.eCourseName, e.eCourseTime, e.eCourseCalory"+
        "                               FROM ExpertCourse as e"+
        "                                   join User_has_ExpertCourse as ue on ue.ExpertCourse_eCourseIdx = e.eCourseIdx"+
        "                               WHERE e.eCourseIdx = ?";         
        int getUserExpertsParam = expertIdx;
        return this.jdbcTemplate.queryForObject(getUserExpertsQuery,
                (rs,rowNum) -> new GetUserExpertRes(
                    rs.getInt("e.eCourseIdx"),
                    rs.getString("e.eCourseName"),
                    rs.getInt("e.eCourseTime"),
                    rs.getInt("e.eCourseCalory")
                ),
                getUserExpertsParam);
    }
    

    public int postUserFindPwd(String phone){
        String postUserFindPwdQuery = "select userIdx from User where userTel=?";
        String postUserFindPwdParams = phone;
        return this.jdbcTemplate.queryForObject(postUserFindPwdQuery,
                (rs, rowNum) ->   
                        rs.getInt("userIdx"),                        
                        postUserFindPwdParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (userEmail, userPwd, userNickname, userTel ) VALUES (?,?,?,?)";
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
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNicknameExist(String nickname){
        String checkNicknameExistQuery = "select exists(select userNickname from User where userNickname = ?)";
        String checkNicknameExistParams = nickname;
        return this.jdbcTemplate.queryForObject(checkNicknameExistQuery,
                int.class,
                checkNicknameExistParams);

    }

    public int checkPhoneExist(String phone){
        String checkPhoneExistQuery = "select exists(select userTel from User where userTel = ?)";
        String checkPhoneExistParams = phone;
        return this.jdbcTemplate.queryForObject(checkPhoneExistQuery,
                int.class,
                checkPhoneExistParams);

    }

    public int checkExpertExist(int expertIdx){
        String checkExpertExistQuery = "select exists(select eCourseIdx from ExpertCourse where eCourseIdx = ?)";
        int checkExpertExistParam = expertIdx;
        return this.jdbcTemplate.queryForObject(checkExpertExistQuery
                                                ,int.class
                                                , checkExpertExistParam);
    }

    public int checkUserExpertExist(int userIdx, int expertIdx){
        String checkUserExpertExistQuery = "select exists(select User_userIdx, ExpertCourse_eCourseIdx from User_has_ExpertCourse where User_userIdx = ? AND ExpertCourse_eCourseIdx = ?)";
        Object[] checkUserExpertExistParam = new Object[]{userIdx,expertIdx};
        return this.jdbcTemplate.queryForObject(checkUserExpertExistQuery
                                                ,int.class
                                                , checkUserExpertExistParam);
    }


    public int editPwd(PatchUserEditPwdReq patchUserEditPwdReq){
        String patchUserEditPwdQuery = "update User set userPwd = ? where userIdx = ? ";
        Object[] patchUserEditPwdParams = new Object[]{patchUserEditPwdReq.getPassword(), patchUserEditPwdReq.getUserIdx()};

        return this.jdbcTemplate.update(patchUserEditPwdQuery,patchUserEditPwdParams);
    }

    public int editNickname(PatchUserEditNicknameReq patchUserEditNicknameReq){
        String patchUserEditNicknameQuery = "update User set userNickname = ? where userIdx = ? ";
        Object[] patchUserEditNicknameParams = new Object[]{patchUserEditNicknameReq.getNickname(), patchUserEditNicknameReq.getUserIdx()};

        return this.jdbcTemplate.update(patchUserEditNicknameQuery,patchUserEditNicknameParams);
    }

    public int editImg(PatchUserEditImgReq patchUserEditImgReq){
        String patchUserEditImgQuery = "update User set userImg = ? where userIdx = ? ";
        Object[] patchUserEditImgParams = new Object[]{patchUserEditImgReq.getImg(), patchUserEditImgReq.getUserIdx()};

        return this.jdbcTemplate.update(patchUserEditImgQuery,patchUserEditImgParams);
    }

    public int deleteUser(int userIdx){
        String deleteUserQuery = "update User set userStatus = 0 where userIdx = ? ";
        Object[] deleteUserParams = new Object[]{userIdx};

        return this.jdbcTemplate.update(deleteUserQuery,deleteUserParams);
    }

    public int editUserGoal(PatchUserEditGoalReq patchUserEditGoalReq){
        String editUserGoalQuery = "update User set userGoal = ? where userIdx = ? ";
        Object[] editUserGoalParams = new Object[]{patchUserEditGoalReq.getGoal(),patchUserEditGoalReq.getUserIdx()};

        return this.jdbcTemplate.update(editUserGoalQuery,editUserGoalParams);
    }

    public int postUserExpert(int userIdx, int expertIdx){
        String postUserExpertQuery = "INSERT INTO User_has_ExpertCourse(User_userIdx, ExpertCourse_eCourseIdx) VALUES (?,?)";
        Object[] postUserExpertParams = new Object[]{userIdx,expertIdx};
        return this.jdbcTemplate.update(postUserExpertQuery,postUserExpertParams);
    }

    public int deleteUserExpert(int userIdx, int expertIdx){
        String deleteUserExpertQuery = "DELETE FROM User_has_ExpertCourse WHERE User_userIdx = ? AND ExpertCourse_eCourseIdx = ?";
        Object[] deleteUserExpertParams = new Object[]{userIdx,expertIdx};
        return this.jdbcTemplate.update(deleteUserExpertQuery,deleteUserExpertParams);
    }


    public GetExerciseTCRes getExerciseTCRes(int exerciseIdx){
        String getExerciseQuery = "SELECT exTime, exCalory FROM Exercise WHERE exIdx = ?";
        int getExerciseParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(getExerciseQuery,
                                (rs, rowNum) -> new GetExerciseTCRes(rs.getInt("exTime")
                                                            ,rs.getInt("exCalory")                                                         
                                                            )
                                                            ,getExerciseParam);
    }


    public int checkExerciseExist(int exerciseIdx){
        String checkExerciseExistQuery = "SELECT exists(SELECT exIdx FROM Exercise WHERE exidx = ?)";
        int checkExerciseExistParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(checkExerciseExistQuery,int.class, checkExerciseExistParam);
    }


    public void createCustomRoutine(int userIdx, int customIdx, PostCustomRoutineReq postCustomRoutineReq){
        String createCustomRoutineQuery = "INSERT INTO CustomCourseRoutine(rep, weight, sets, rest, Exercise_exIdx, CustomCourse_cCourseIdx, CustomCourse_User_userIdx) VALUES (?,?,?,?,?,?,?)";
        Object[] createCustomRoutineParams = new Object[]{postCustomRoutineReq.getRep(),postCustomRoutineReq.getWeight(),postCustomRoutineReq.getSet(),postCustomRoutineReq.getRest(),postCustomRoutineReq.getExerciseIdx(),customIdx,userIdx};
        this.jdbcTemplate.update(createCustomRoutineQuery, createCustomRoutineParams);
    }

    public int createCustom(int userIdx, String name, String part, String detail, String intro){
        String createCustomQuery = "INTSERT INTO CustomCourse(cCourseName, cCoursePart, cCourseDetailPart, cCourseIntroduce, User_userIdx) VALUES (?,?,?,?,?)";
        Object[] createCustomParams = new Object[]{name,part,detail,intro,userIdx};
        this.jdbcTemplate.update(createCustomQuery, createCustomParams);

        String lastInsertIdxQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }

    public void addCustomTC(int customIdx,int times,int calories){
        String addCustomTCQuery = "update CustomCourse set cCourseTime = ?, cCourseCalory = ? where cCourseIdx = ? ";
        Object[] addCustomTCParams = new Object[]{times,calories,customIdx};
        this.jdbcTemplate.update(addCustomTCQuery,addCustomTCParams);
    }
}
