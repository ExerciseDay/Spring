package com.exerciseday.dev.src.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.exercise.model.ExerciseInfo;
import com.exerciseday.dev.src.exercise.model.*;

import java.util.List;

import javax.sql.DataSource;


@Repository
public class ExerciseDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public int createExercise(PostExerciseReq postExerciseReq){
        String createExerciseQuery ="INSERT INTO Exercise(exName,exPart,exDetailPart,exInfo,exImg,exTime,exCalory,exIntroduce) values (?,?,?,?,?,?,?,?)";
        Object[] createExerciseParams = new Object[]{postExerciseReq.getExerciseName()
                                                    ,postExerciseReq.getExercisePart()
                                                    ,postExerciseReq.getExerciseDetailPart()
                                                    ,postExerciseReq.getExerciseInfo()
                                                    ,postExerciseReq.getExerciseImg()
                                                    ,postExerciseReq.getExerciseTime()
                                                    ,postExerciseReq.getExerciseCalory()
                                                    ,postExerciseReq.getExerciseIntroduce()};
        this.jdbcTemplate.update(createExerciseQuery, createExerciseParams);              

        String lastInsertIdQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);                                                
    }

    public Exercise getExercise(int exerciseIdx){
        String getExerciseQuery = "SELECT exIdx, exName, exPart, exDetailPart, exInfo, exImg, exTime, exCalory, exIntroduce FROM Exercise WHERE exIdx = ?";
        int getExerciseParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(getExerciseQuery,
                                (rs, rowNum) -> new Exercise(rs.getInt("exIdx")
                                                            ,rs.getString("exName")
                                                            ,rs.getString("exPart")
                                                            ,rs.getString("exDetailPart")
                                                            ,rs.getString("exInfo")
                                                            ,rs.getString("exImg")
                                                            ,rs.getInt("exTime")
                                                            ,rs.getInt("exCalory")
                                                            ,rs.getString("exIntroduce")
                                                            ),getExerciseParam);
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

    /* 
    public int getExCount(String exerciseName){
        String getExercisesQuery = "SELECT Count(exIdx) as count FROM Exercise WHERE exName like ?";
        String getExercisesParam = exerciseName;
        return this.jdbcTemplate.queryForObject(getExercisesQuery,int.class, getExercisesParam);
    }
    */
    
        //운동 검색
    public int getCount(String exNameSearchForm){
        String getExercisesQuery = "SELECT Count(exIdx) as count FROM Exercise WHERE (exName LIKE ? OR exDetailPart LIKE ?)";
        Object[] getExercisesParam = new Object[]{exNameSearchForm, exNameSearchForm};
        //String getExercisesParam = exNameSearchForm;
        return this.jdbcTemplate.queryForObject(getExercisesQuery,
                                                int.class,getExercisesParam);
    }

    public List<ExerciseInfo> getExInfos(String exNameSearchForm){
        String getExercisesQuery = "SELECT exIdx, exName, exPart, exDetailPart, exIntroduce \n"+
                                    "FROM Exercise \n"+        
                                    "WHERE( \n"+
                                    "exName LIKE ? OR \n"+                                    
                                    "exDetailPart LIKE ? )";
                                    
        Object[] getExercisesParam = new Object[]{exNameSearchForm, exNameSearchForm};
        //String getExercisesParam = exNameSearchForm;
        return this.jdbcTemplate.query(getExercisesQuery,
                                                (rs, rowNum)->
                                                new ExerciseInfo(rs.getInt("exIdx"),
                                                rs.getString("exName"),
                                                rs.getString("exPart"),
                                                rs.getString("exDetailPart"),
                                                rs.getString("exIntroduce"))
                                                ,getExercisesParam);
    }
    
    //운동 검색    
    /*
    public GetExercisesRes getExercises(String exNameSearchForm){
        String getExercisesQuery = "SELECT Count(exIdx) as count FROM Exercise WHERE (exName LIKE ? OR exDetailPart LIKE ?)";
        Object getExercisesParams = new Object[]{exNameSearchForm,exNameSearchForm};
        //String getExercisesParam = exNameSearchForm;
        return this.jdbcTemplate.queryForObject(getExercisesQuery,
                                                (rs,rowNum) -> new GetExercisesRes(
                                                                    rs.getInt("count"),
                                                                    this.jdbcTemplate.query("SELECT exIdx, exName, exPart, exDetailPart, exIntroduce \n"+
                                                                                            "FROM Exercise \n"+                                                                                            
                                                                                            "WHERE( \n"+
                                                                                            "exName LIKE ? OR \n"+
                                                                                            
                                                                                            "exDetailPart LIKE ?  \n"+
                                                                                            ")",                                                                                            
                                                                                            (rk,rownum)->new ExerciseInfo(
                                                                                                            rk.getInt("exIdx"),
                                                                                                            rk.getString("exName"),
                                                                                                            rk.getString("exPart"),
                                                                                                            rk.getString("exDetailPart"),
                                                                                                            rk.getString("exIntroduce")
                                                                                                         )
                                                                                            ,getExercisesParams)
                                                                )
                                                                ,getExercisesParams);
    }
    */
    
    public GetDibsRes getDibs(int userIdx){
        String getDibsResQuery = ""+
                                "SELECT u.userIdx, u.userNickname, IF(dibsCount is null, 0, dibsCount) as dibsCount\n"+
                                "FROM User as u\n"+
                                "   left join(SELECT User_userIdx, Count(Exercise_exIdx) as dibsCount\n"+
                                "             FROM User_has_Exercise\n"+
                                "             GROUP BY User_userIdx) ue on ue.User_userIdx = u.userIdx\n"+
                                "WHERE u.userIdx = ?";
        int getDibsResParam = userIdx;
        return this.jdbcTemplate.queryForObject(getDibsResQuery,
                                                (rk, rownum) -> new GetDibsRes(
                                                                rk.getInt("u.userIdx"),
                                                                rk.getString("u.userNickname"),
                                                                rk.getInt("dibsCount"),                                                
                                                                this.jdbcTemplate.query("SELECT e.exIdx, e.exName, e.exPart, e.exDetailPart, e.exIntroduce\n"+
                                                                                        "FROM Exercise as e\n"+
                                                                                        "   join User_has_Exercise as ue on ue.Exercise_exIdx = e.exIdx\n"+
                                                                                        "   join User as u on u.userIdx = ue.User_userIdx\n"+
                                                                                        "WHERE u.userIdx = ?",
                                                                                        (rs, rowNum)->new ExerciseInfo( 
                                                                                                        rs.getInt("e.exIdx"),
                                                                                                        rs.getString("e.exName"),
                                                                                                        rs.getString("e.exPart"),
                                                                                                        rs.getString("e.exDetailPart"),
                                                                                                        rs.getString("e.exIntroduce")) 
                                                                                        ,rk.getInt("u.userIdx"))
                                                                ),getDibsResParam);
    }
    
    public int checkExerciseExist(int exerciseIdx){
        String checkExerciseExistQuery = "SELECT exists(SELECT exIdx FROM Exercise WHERE exIdx = ?)";
        int checkExerciseExistParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(checkExerciseExistQuery,int.class, checkExerciseExistParam);
    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "SELECT exists(SELECT userIdx FROM User WHERE userIdx = ?)";
        int checkUserExistParam = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,int.class, checkUserExistParam);
    }


    public int deleteExercise(int exerciseIdx){
        String deleteExerciseQuery = "DELETE FROM Exercise WHERE exIdx = ?";
        int deleteExerciseParam = exerciseIdx;
        return this.jdbcTemplate.update(deleteExerciseQuery, deleteExerciseParam);
    }

    public void postDibs(int exerciseIdx, int userIdx){
        String postDibsQuery = "INSERT INTO User_has_Exercise(Exercise_exIdx, User_userIdx) VALUES(?,?)";
        Object[] postDibsParams = new Object[]{exerciseIdx,userIdx};
        this.jdbcTemplate.update(postDibsQuery, postDibsParams);
    }
    public int deleteDibs(int exerciseIdx, int userIdx){
        String deleteDibsQuery = "DELETE FROM User_has_Exercise WHERE Exercise_exIdx = ? AND User_userIdx = ?";
        Object[] deleteDibsParams = new Object[]{exerciseIdx,userIdx};
        return this.jdbcTemplate.update(deleteDibsQuery, deleteDibsParams);
    }
}
