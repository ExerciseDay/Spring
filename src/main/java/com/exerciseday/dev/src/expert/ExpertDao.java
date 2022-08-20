package com.exerciseday.dev.src.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.expert.model.*;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ExpertDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*
    public int createExpert(String name, String part, String detail){
        String createExpertQuery = "INSERT INTO ExpertCourse(eCourseName, eCoursePart, eCourseDetailPart,User_userIdx) VALUES (?,?,?,1)";
        Object[] createExpertParams = new Object[] {name,part,detail};
        this.jdbcTemplate.update(createExpertQuery,
                createExpertParams);

        String lastInsertIdxQuery = "select last_insert_id()"; //자동으로 가장 마지막에 들어간 idx를 리턴함
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);

    }*/

    public int createExpert(String name, String part, String detail, String intro){
        String createExpertQuery = "INSERT INTO ExpertCourse(eCourseName, eCoursePart, eCourseDetailPart,eCourseIntroduce) VALUES (?,?,?,?)";
        Object[] createExpertParams = new Object[] {name,part,detail,intro};
        this.jdbcTemplate.update(createExpertQuery,
                createExpertParams);

        String lastInsertIdxQuery = "select last_insert_id()"; //자동으로 가장 마지막에 들어간 idx를 리턴함
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);

    }
    /* 
    public void createExpertRoutine(int expertIdx, PostExpertRoutineReq expertRoutine){
        String createExpertRoutineQuery = "INSERT INTO ExpertCourseRoutine(rep,weight,sets,rest,ExpertCourse_eCourseIdx,ExpertCourse_User_userIdx,Exercise_exIdx) VALUES(?,?,?,?,?,1,?)";
        Object [] createExpertRoutineParams = new Object[]{expertRoutine.getRep(),expertRoutine.getWeight(),expertRoutine.getSet(),expertRoutine.getRest(),expertIdx,expertRoutine.getExerciseIdx()};
        this.jdbcTemplate.update(createExpertRoutineQuery, createExpertRoutineParams);              
    }
    */

    public void createExpertRoutine(int expertIdx, PostExpertRoutineReq expertRoutine){
        String createExpertRoutineQuery = "INSERT INTO ExpertCourseRoutine(rep,weight,sets,rest,ExpertCourse_eCourseIdx,Exercise_exIdx) VALUES(?,?,?,?,?,?)";
        Object [] createExpertRoutineParams = new Object[]{expertRoutine.getRep(),expertRoutine.getWeight(),expertRoutine.getSet(),expertRoutine.getRest(),expertIdx,expertRoutine.getExerciseIdx()};
        this.jdbcTemplate.update(createExpertRoutineQuery, createExpertRoutineParams);              
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

    public void addExpertTC(int expertIdx,int times,int calories){
        String addExpertTCQuery = "update ExpertCourse set eCourseTime = ?, eCourseCalory = ? where eCourseIdx = ? ";
        Object[] addExpertTCParams = new Object[]{times,calories,expertIdx};
        this.jdbcTemplate.update(addExpertTCQuery,addExpertTCParams);
    }
    

    public List<ExpertByPart> getExpertsByPart(GetExpertByPartReq getExpertByPartReq,int offset){
        String getExpertsByPartQuery="SELECT eCourseIdx, eCourseName, eCourseIntroduce, eCourseTime, eCourseCalory, eCoursePart, eCourseDetailPart \n"+
                                    "FROM ExpertCourse\n" +
                                    "WHERE eCoursePart = ? AND eCourseDetailPart = ? \n" +
                                    "LIMIT ?,8";

        Object[] getExpertsByPartParams = new Object[]{getExpertByPartReq.getPart(),getExpertByPartReq.getDetailPart(),offset};
        return this.jdbcTemplate.query(getExpertsByPartQuery,
                    (rs,rowNum) -> new ExpertByPart(
                        rs.getInt("eCourseIdx"),
                        rs.getString("eCourseName"),
                        rs.getString("eCourseIntroduce"),
                        rs.getInt("eCourseTime"),
                        rs.getInt("eCourseCalory"),
                        rs.getString("eCoursePart"),
                        rs.getString("eCourseDetailPart")
                        )
                        ,getExpertsByPartParams);
    }

    public ExpertNTC getExpertNTC(int expertIdx){
        String getExpertNTCQuery = "select eCourseIdx, eCourseName, eCourseTime, eCourseCalory from ExpertCourse where eCourseIdx = ?";
        int getExpertNTCParam = expertIdx;
        return this.jdbcTemplate.queryForObject(getExpertNTCQuery
                                        ,(rs, rowNum) -> new ExpertNTC(
                                                                rs.getInt("eCourseIdx"),
                                                                rs.getString("eCourseName"),
                                                                rs.getInt("eCourseTime"),
                                                                rs.getInt("eCourseCalory")                                                                
                                                            ) 
                                        ,getExpertNTCParam);
    }

    public List<ExpertRoutine> getExpertRoutines(int expertIdx){
        String getExpertRoutineQuery = "SELECT er.eCourseRoutineIdx, \n"+
                                                "er.rep,\n"+
                                                "er.weight,\n"+ 
                                                "er.sets,\n" +
                                                "er.rest,\n" +
                                                "er.Exercise_exIdx \n" +
                                        "FROM ExpertCourseRoutine as er \n"+
                                        "   join ExpertCourse as e on e.eCourseIdx = er.ExpertCourse_eCourseIdx\n "+
                                        "WHERE e.eCourseIdx = ?";
        int getExpertRoutineParam = expertIdx;
        return this.jdbcTemplate.query(getExpertRoutineQuery,
                                                (rs,rowNum)->new ExpertRoutine(
                                                    rs.getInt("er.eCourseRoutineIdx"),
                                                    rs.getInt("er.rep"),
                                                    rs.getInt("er.weight"),
                                                    rs.getInt("er.sets"),
                                                    rs.getInt("er.rest"),
                                                    rs.getInt("er.exIdx")) 
                                                ,getExpertRoutineParam);
    }

    public List<GetExpertRoutineInfoRes> getExpertRoutineInfos(int expertIdx){
        String getExpertRoutineInfoQuery="SELECT er.eCourseRoutineIdx, "+
                                                //"ex.exIdx, "+
                                        "       ex.exName, "+
                                        "       ex.exPart, "+
                                        "       ex.exDetailPart, "+
                                        "       ex.exInfo, "+
                                        "       ex.exImg, "+
                                        "       ex.exTime, "+
                                        "       ex.exCalory, "+
                                        "       ex.exIntroduce,"+
                                        "       e.eCourseIdx\n "+
                                        "FROM Exercise as ex\n "+
                                        "   join ExpertCourseRoutine as er on er.Exercise_exIdx = ex.exIdx\n "+     
                                        "   join ExpertCourse as e on e.eCourseIdx = er.ExpertCourse_eCourseIdx \n"+        
                                        "WHERE e.eCourseIdx = ? ";
        int getExpertRoutineInfoParam = expertIdx;
        return this.jdbcTemplate.query(getExpertRoutineInfoQuery,
                                        (rs,rowNum) -> new GetExpertRoutineInfoRes(
                                            rs.getInt("er.eCourseRoutineIdx"),
                                            //rs.getInt("ex.exIdx"),
                                            rs.getString("ex.exName"),
                                            rs.getString("ex.exPart"),
                                            rs.getString("ex.exDetailPart"),
                                            rs.getString("ex.exInfo"),
                                            rs.getString("ex.exImg"),
                                            rs.getInt("ex.exTime"),
                                            rs.getInt("ex.exCalory"),
                                            rs.getString("ex.exIntroduce"),
                                            rs.getInt("e.eCourseIdx")
                                        )
                                        ,getExpertRoutineInfoParam);
    }

    public int checkTrainerExist(int trainerIdx){
        String checkTrainerExistQuery = "select exists(select trainerIdx from Trainer where trainerIdx = ?)";
        int checkTrainerExistParam = trainerIdx;
        return this.jdbcTemplate.queryForObject(checkTrainerExistQuery,
                int.class,
                checkTrainerExistParam);
    }

    public int checkExerciseExist(int exerciseIdx){
        String checkExerciseExistQuery = "select exists(select exIdx from Exercise where exIdx = ?)";
        int checkExerciseExistParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(checkExerciseExistQuery,
                                                int.class, 
                                                checkExerciseExistParam);
    }

    public int checkExpertExist(int expertIdx){
        String checkExpertExistQuery = "select exists(select eCourseIdx from ExpertCourse where eCourseIdx = ?)";
        int checkExpertExistParam = expertIdx;
        return this.jdbcTemplate.queryForObject(checkExpertExistQuery
                                                ,int.class
                                                , checkExpertExistParam);
    }
    
}
