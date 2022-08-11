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
    //userIdx 어떻게 하지?
    public int createExpert(int trainerIdx, String name, String part, String detail){
        String createExpertQuery = "INSERT INTO ExpertCourse(eCourseName, eCoursePart, eCourseDetailPart,trainerIdx) VALUES (?,?,?,?)";
        Object[] createExpertParams = new Object[] {name,part,detail,trainerIdx};
        this.jdbcTemplate.update(createExpertQuery,
                createExpertParams);

        String lastInsertIdxQuery = "select last_insert_id()"; //자동으로 가장 마지막에 들어간 idx를 리턴함
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);

    }

    public int createExpertRoutine(int expertIdx, ExpertRoutine expertRoutine){
        String createExpertRoutineQuery = "INSERT INTO ExpertCourseRoutine(rep,weight,set,rest,eCourseIdx,exIdx) VALUES(?,?,?,?,?,?)";
        Object [] createExpertRoutineParams = new Object[]{expertRoutine.getRep(),expertRoutine.getWeight(),expertRoutine.getSet(),expertRoutine.getRest(),expertIdx,expertRoutine.getExerciseIdx()};
        this.jdbcTemplate.update(createExpertRoutineQuery, createExpertRoutineParams);

        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }

    //////////////////////////////////////////////
    public List<ExpertByPart> getExpertsByPart(GetExpertByPartReq getExpertByPartReq){
        String getExpertsByPartQuery="";

        Object[] getExpertsByPartParams = new Object[]{getExpertByPartReq.getPart(),getExpertByPartReq.getDetailPart()};
        return this.jdbcTemplate.query(getExpertsByPartQuery,
                    (rs,rowNum) -> new ExpertByPart(
                        rs.getInt("eCourseIdx"),
                        rs.getString("eCourseName"),
                        rs.getString("eCourseIntroduce"),
                        rs.getInt("eCourseTime"),
                        rs.getInt("eCourseCalory")
                        )
                        ,getExpertsByPartParams);
    }

    public ExpertNTC getExpertNTC(int expertIdx){
        String getExpertNTCQuery = "select eCourseName, eCourseTime, eCourseCalory from ExpertCourse where eCourseIdx = ?";
        int getExpertNTCParam = expertIdx;
        return this.jdbcTemplate.queryForObject(getExpertNTCQuery
                                        ,(rs, rowNum) -> new ExpertNTC(
                                                                rs.getString("eCourseName"),
                                                                rs.getInt("eCourseTime"),
                                                                rs.getInt("eCourseCalory")                                                                
                                                            ) 
                                        ,getExpertNTCParam);
    }

    public List<ExpertRoutine> getExpertRoutines(int expertIdx){
        String getExpertRoutineQuery = "SELECT er.eCourseRoutineIdx, "+
                                                "er.rep,"+
                                                "er.weight,"+ 
                                                "er.set," +
                                                "er.rest," +
                                                "er.exIdx " +
                                        "FROM ExpertCourseRoutine as er "+
                                        "join ExpertCourse as e on e.eCourseIdx = er.eCourseIdx "+
                                        "WHERE e.eCourseIdx = ?";
        int getExpertRoutineParam = expertIdx;
        return this.jdbcTemplate.query(getExpertRoutineQuery,
                                                (rs,rowNum)->new ExpertRoutine(
                                                    rs.getInt("er.eCourseRoutineIdx"),
                                                    rs.getInt("er.rep"),
                                                    rs.getInt("er.weight"),
                                                    rs.getInt("er.set"),
                                                    rs.getInt("er.rest"),
                                                    rs.getInt("er.exIdx")) 
                                                ,getExpertRoutineParam);
    }

    public List<GetExpertRoutineInfoRes> getExpertRoutineInfos(int expertIdx){
        String getExpertRoutineInfoQuery="SELECT er.eCourseRoutineIdx, "+
                                                //"ex.exIdx, "+
                                                "ex.exName, "+
                                                "ex.exPart, "+
                                                "ex.exDetailPart, "+
                                                "ex.exInfo, "+
                                                "ex.exImg, "+
                                                "ex.exTime, "+
                                                "ex.exCalory, "+
                                                "ex.exIntroduce"+
                                                "FROM Exercise as ex "+
                                                "join ExpertCourseRoutine as er on er.exIdx = ex.exIdx "+     
                                                "join ExpertCourse as e on e.eCourseIdx = er.eCourseIdx "+        
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
                                            rs.getString("ex.exIntroduce")
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
