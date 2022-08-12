package com.exerciseday.dev.src.gym;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exerciseday.dev.src.gym.model.*;

@Repository
public class GymDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<GetGymRes> selectGym(String univ){
        String selectGymQuery = "SELECT a.gymIdx, a.gymName, a.gymIntroduce,\n" +
        " a.gymImg, a.gymDistance, b.rvSP, a.univ\n" +
        "FROM exercisedaydb.gym AS a\n" +
        "INNER JOIN exercisedaydb.review AS b\n" +
        "ON a.gymIdx = b.Gym_gymIdx\n" +
        "WHERE a.univ = ?";

        String selectGymParam = univ;

       return this.jdbcTemplate.query(selectGymQuery,
                (rs,rowNum) -> new GetGymRes(
                    rs.getInt("gymIdx"),
                    rs.getString("gymName"),
                    rs.getString("gymIntroduce"),
                    rs.getString("gymImg"),
                    rs.getInt("gymDistance"),
                    rs.getInt("rvSP"),
                    rs.getString("univ")
                ),selectGymParam);

    }

    public GetGymInfoRes selectGymInfo(int gymIdx){
        String selectGymInfoQuery = "SELECT * FROM gym WHERE gymIdx = ?";
        int selectGymInfoParam = gymIdx;
        return this.jdbcTemplate.queryForObject(selectGymInfoQuery,
                (rs,rowNum) -> new GetGymInfoRes(
                    rs.getInt("gymIdx"),
                    rs.getString("gymName"),
                    rs.getString("gymAddress"),
                    rs.getString("gymIntroduce"),
                    rs.getString("gymImg"),
                    rs.getString("gymTime"),
                    rs.getInt("gymParking"),
                    rs.getInt("gymSauna"),
                    rs.getInt("gymCloths"),
                    rs.getInt("gymShower"),
                    rs.getInt("gymDistance")
                ), selectGymInfoParam);
    }

    public List<GetTrainersRes> selectTrainers(int gymIdx){
        String selectTrainersQuery = "SELECT * FROM trainer WHERE gymIdx = ?";
        int selectTrainersParam = gymIdx;
        return this.jdbcTemplate.query(selectTrainersQuery,
                (rs,rowNum) -> new GetTrainersRes(
                    rs.getInt("gymIdx"),
                    rs.getString("trainerName"),
                    rs.getString("trainerCareer"),
                    rs.getString("trainerIntroduce"),
                    rs.getString("trainerImg")
                ), selectTrainersParam);
    }
}
