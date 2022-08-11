package com.exerciseday.dev.src.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.exercise.model.*;

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

    public int checkExerciseExist(int exerciseIdx){
        String checkExerciseExistQuery = "SELECT exists(SELECT exIdx FROM Exercise WHERE exidx = ?)";
        int checkExerciseExistParam = exerciseIdx;
        return this.jdbcTemplate.queryForObject(checkExerciseExistQuery,int.class, checkExerciseExistParam);
    }

    public int deleteExercise(int exerciseIdx){
        String deleteExerciseQuery = "DELETE FROM Exercise WHERE exIdx = ?";
        int deleteExerciseParam = exerciseIdx;
        return this.jdbcTemplate.update(deleteExerciseQuery, deleteExerciseParam);
    }

}
