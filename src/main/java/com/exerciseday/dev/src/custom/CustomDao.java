package com.exerciseday.dev.src.custom;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.custom.model.CustomNTC;
import com.exerciseday.dev.src.custom.model.CustomRoutine;
import com.exerciseday.dev.src.custom.model.GetCustomRoutineInfoRes;
import com.exerciseday.dev.src.custom.model.GetExerciseTCRes;
import com.exerciseday.dev.src.custom.model.PatchCustomRes;
import com.exerciseday.dev.src.custom.model.PostCustomReq;
import com.exerciseday.dev.src.custom.model.PostCustomRoutineReq;

@Repository
public class CustomDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createCustom(int userIdx, String name, String intro){
        String createCustomQuery = "INSERT INTO CustomCourse(cCourseName,  cCourseIntroduce, User_userIdx) VALUES (?,?,?)";
        Object[] createCustomParams = new Object[]{name,intro,userIdx};
        this.jdbcTemplate.update(createCustomQuery, createCustomParams);

        String lastInsertIdxQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }


    public GetExerciseTCRes getExerciseTCRes(int exerciseIdx){
        String getExerciseQuery = "SELECT exIdx, exTime, exCalory FROM Exercise WHERE exIdx = ?";
        int getExerciseParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(getExerciseQuery,
                                (rs, rowNum) -> new GetExerciseTCRes(
                                                            rs.getInt("exIdx")
                                                            ,rs.getInt("exTime")
                                                            ,rs.getInt("exCalory")                                                         
                                                            )
                                                            ,getExerciseParam);
    }

    public void createCustomRoutine(int userIdx, int customIdx, PostCustomRoutineReq postCustomRoutineReq){
        String createCustomRoutineQuery = "INSERT INTO CustomCourseRoutine(rep, weight, sets, rest, Exercise_exIdx, CustomCourse_cCourseIdx, CustomCourse_User_userIdx) VALUES (?,?,?,?,?,?,?)";
        Object[] createCustomRoutineParams = new Object[]{postCustomRoutineReq.getRep(),postCustomRoutineReq.getWeight(),postCustomRoutineReq.getSet(),postCustomRoutineReq.getRest(),postCustomRoutineReq.getExerciseIdx(),customIdx,userIdx};
        this.jdbcTemplate.update(createCustomRoutineQuery, createCustomRoutineParams);
    }

    public void addCustomTC(int customIdx,int times,int calories){
        String addCustomTCQuery = "update CustomCourse set cCourseTime = ?, cCourseCalory = ? where cCourseIdx = ? ";
        Object[] addCustomTCParams = new Object[]{times,calories,customIdx};
        this.jdbcTemplate.update(addCustomTCQuery,addCustomTCParams);
    }

    public CustomNTC getCustomNTC(int userIdx,int customIdx){
        String getCustomNTCQuery = "SELECT c.cCourseIdx, c.cCourseName, c.cCourseTime, c.cCourseCalory\n"+
        "                           FROM CustomCourse as c\n"+       
        "                               join User as u on u.userIdx = c.User_userIdx\n"+        
        "                           WHERE u.userIdx = ? AND c.cCourseIdx = ?";
        Object[] getCustomNTCParams = new Object[]{userIdx, customIdx};
        return this.jdbcTemplate.queryForObject(getCustomNTCQuery
                                        ,(rs, rowNum) -> new CustomNTC(
                                                                rs.getInt("c.cCourseIdx"),
                                                                rs.getString("c.cCourseName"),
                                                                rs.getInt("c.cCourseTime"),
                                                                rs.getInt("c.cCourseCalory")                                                                
                                                            ) 
                                        ,getCustomNTCParams);
    }


    public List<CustomRoutine> getCustomRoutines(int userIdx, int customIdx){
        String getCustomRoutineQuery = "SELECT cr.cCourseRoutineIdx, cr.rep, cr.weight, cr.sets, cr.rest, cr.Exercise_exIdx\n"+
                                        "FROM CustomCourseRoutine as cr\n"+        
                                        "join CustomCourse as c on c.cCourseIdx = cr.CustomCourse_cCourseIdx\n"+        
                                        "join User as u on u.userIdx = cr.CustomCourse_User_userIdx\n"+        
                                        "WHERE u.userIdx = ? AND c.cCourseIdx = ?";
        Object[] getCustomRoutineParam = new Object[]{userIdx, customIdx};
        return this.jdbcTemplate.query(getCustomRoutineQuery,
                                                (rs,rowNum)->new CustomRoutine(
                                                    rs.getInt("cr.cCourseRoutineIdx"),
                                                    rs.getInt("cr.rep"),
                                                    rs.getInt("cr.weight"),
                                                    rs.getInt("cr.sets"),
                                                    rs.getInt("cr.rest"),
                                                    rs.getInt("cr.Exercise_exIdx")) 
                                                ,getCustomRoutineParam);
    }

    public List<GetCustomRoutineInfoRes> getCustomRoutineInfos(int userIdx, int customIdx){
        String getCustomRoutineInfoQuery="SELECT cr.cCourseRoutineIdx, ex.exName, ex.exPart, ex.exDetailPart, ex.exInfo, ex.exImg, ex.exTime, ex.exCalory, ex.exIntroduce, c.cCourseIdx\n"+
        "                                   FROM Exercise as ex\n"+        
        "                                       join CustomCourseRoutine as cr on cr.Exercise_exIdx = ex.exIdx\n"+        
        "                                       join CustomCourse as c on c.cCourseIdx = cr.CustomCourse_cCourseIdx\n"+        
        "                                       join User as u on u.userIdx = c.User_userIdx\n"+        
        "                                   WHERE u.userIdx = ? AND c.cCourseIdx = ?";
        
        Object[] getCustomRoutineInfoParams = new Object[]{userIdx,customIdx};
        return this.jdbcTemplate.query(getCustomRoutineInfoQuery,
                                        (rs,rowNum) -> new GetCustomRoutineInfoRes(
                                            rs.getInt("cr.cCourseRoutineIdx"),
                                            //rs.getInt("ex.exIdx"),
                                            rs.getString("ex.exName"),
                                            rs.getString("ex.exPart"),
                                            rs.getString("ex.exDetailPart"),
                                            rs.getString("ex.exInfo"),
                                            rs.getString("ex.exImg"),
                                            rs.getInt("ex.exTime"),
                                            rs.getInt("ex.exCalory"),
                                            rs.getString("ex.exIntroduce"),
                                            rs.getInt("c.cCourseIdx")
                                        )
                                        ,getCustomRoutineInfoParams);
    }

    public int checkExerciseExist(int exerciseIdx){
        String checkExerciseExistQuery = "select exists(select exIdx from Exercise where exIdx = ?)";
        int checkExerciseExistParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(checkExerciseExistQuery,
                                                int.class, 
                                                checkExerciseExistParam);
    }

    public int checkCustomExist(int customIdx){
        String checkCustomExistQuery = "select exists(select cCourseIdx from CustomCourse where cCourseIdx = ?)";
        int checkCustomExistParam = customIdx;
        return this.jdbcTemplate.queryForObject(checkCustomExistQuery,
                                                int.class, 
                                                checkCustomExistParam);
    }

    public int checkCustomRoutineExist(int customRoutineIdx){
        String checkCustomRoutineExistQuery = "select exists(select cCourseRoutineIdx from CustomCourseRoutine where cCourseRoutineIdx = ?)";
        int checkCustomRoutineExistParam = customRoutineIdx;
        return this.jdbcTemplate.queryForObject(checkCustomRoutineExistQuery,int.class ,checkCustomRoutineExistParam);
    }

    public int deleteCustom(int customIdx){
        String deleteCustomQuery1 = "DELETE FROM CustomCourseRoutine WHERE CustomCourse_cCourseIdx = ?";
        int deleteCustomParam = customIdx;
        this.jdbcTemplate.update(deleteCustomQuery1,  deleteCustomParam);
        String deleteCustomQuery2 = "DELETE FROM CustomCourse WHERE cCourseIdx = ?";
        return this.jdbcTemplate.update(deleteCustomQuery2, deleteCustomParam);
    }

    public int removeCustomRoutine(int userIdx, int customIdx,int customRoutineIdx){
        String removeCustomRoutineQuery = "DELETE FROM CustomCourseRoutine WHERE CustomCourse_User_userIdx = ? AND CustomCourse_cCourseIdx = ? AND cCourseRoutineIdx = ?";
        Object[] removeCustomRoutineParam = new Object[]{userIdx,customIdx, customRoutineIdx};
        return this.jdbcTemplate.update(removeCustomRoutineQuery, removeCustomRoutineParam);
    }
}
