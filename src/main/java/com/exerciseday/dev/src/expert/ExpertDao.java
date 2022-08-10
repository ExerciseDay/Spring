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

    //////////////////////////////////////////쿼리 짜기 PostDao selectPosts
    public List<ExpertByPart> getExpertsByPart(GetExpertByPartReq getExpertByPartReq){
        String getExpertsByPartQuery="";

        Object[] getExpertsByPartParams = new Object[]{getExpertByPartReq.getPart(),getExpertByPartReq.getDetailPart()};
        return this.jdbcTemplate.query(getExpertsByPartQuery,
                    (rs,rowNum) -> new ExpertByPart(
                        rs.getInt("expertIdx"),
                        rs.getString("expertName"),
                        rs.getString("expertIntroduce"),
                        rs.getInt("expertTime"),
                        rs.getInt("expertCalory")
                        )
                        ,getExpertsByPartParams);
    }


    public int checkTrainerExist(int trainerIdx){
        String checkTrainerExistQuery = "select exists(select trainerIdx from Trainer where trainerIdx = ?)";
        int checkTrainerExistParam = trainerIdx;
        return this.jdbcTemplate.queryForObject(checkTrainerExistQuery,
                int.class,
                checkTrainerExistParam);
    }
}
