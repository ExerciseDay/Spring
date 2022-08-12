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
        " a.gymImg, a.gymDistance, b.rvSP\n" +
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
                    rs.getInt("spoint"),
                    rs.getString("univ")
                ),selectGymParam);

    }
}
