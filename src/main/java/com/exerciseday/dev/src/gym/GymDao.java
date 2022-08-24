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
        String selectGymQuery = "SELECT a.gymIdx, a.gymName, a.gymAddress, c.gymImg," +
        "a.gymDistance, a.univ, AVG(b.rvSP) as gymSP,\n" +
        "a.gymParking, a.gymSauna, a.gymCloths, a.gymShower\n" +
        "FROM gym as a\n" +
        "INNER JOIN review as b\n" +
        "ON a.gymIdx = b.Gym_gymIdx\n" +
        "INNER JOIN gymImg as c\n" +
        "ON a.gymIdx = c.Gym_gymIdx\n" +
        "WHERE a.univ = ?\n" +
        "GROUP BY gymIdx";

        String selectGymParam = univ;

       return this.jdbcTemplate.query(selectGymQuery,
                (rs,rowNum) -> new GetGymRes(
                    rs.getInt("gymIdx"),
                    rs.getString("gymName"),
                    rs.getString("gymAddress"),
                    rs.getString("gymImg"),
                    rs.getInt("gymDistance"),
                    rs.getString("univ"),
                    rs.getInt("gymParking"),
                    rs.getInt("gymSauna"),
                    rs.getInt("gymCloths"),
                    rs.getInt("gymShower"),
                    rs.getDouble("gymSP")
                ),selectGymParam);

    }

    public List<GetGymRes> searchGym(String keyword){
        String searchGymQuery = "SELECT a.gymIdx, a.gymName, a.gymIntroduce, c.gymImg," +
        "a.gymDistance, a.univ, AVG(b.rvSP) as gymSP,\n" +
        "a.gymParking, a.gymSauna, a.gymCloths, a.gymShower\n" +
        "FROM gym as a\n" +
        "INNER JOIN review as b\n" +
        "ON a.gymIdx = b.Gym_gymIdx\n" +
        "INNER JOIN gymImg as c\n" +
        "ON a.gymIdx = c.Gym_gymIdx\n" +
        "where a.gymName LIKE ?\n" +
        "GROUP BY gymIdx";

        String searchGymParam = keyword;

       return this.jdbcTemplate.query(searchGymQuery,
                (rs,rowNum) -> new GetGymRes(
                    rs.getInt("gymIdx"),
                    rs.getString("gymName"),
                    rs.getString("gymIntroduce"),
                    rs.getString("gymImg"),
                    rs.getInt("gymDistance"),
                    rs.getString("univ"),
                    rs.getInt("gymParking"),
                    rs.getInt("gymSauna"),
                    rs.getInt("gymCloths"),
                    rs.getInt("gymShower"),
                    rs.getDouble("gymSP")
                ),searchGymParam);

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

    public List<GetReviewRes> selectReview(int gymIdx){
        String selectReviewsQuery = "SELECT a.rvIdx, a.rvContent, a.rvSP, b.userNickName\n"
        + "FROM exercisedaydb.review as a\n"
        + "INNER JOIN exercisedaydb.user as b\n"
        + "on a.User_userIdx = b.userIdx\n"
        + "WHERE a.Gym_gymIdx = ?";
        int selectReviewsParam = gymIdx;
        return this.jdbcTemplate.query(selectReviewsQuery,
                (rs,rowNum) -> new GetReviewRes(
                    rs.getInt("rvIdx"),
                    rs.getString("rvContent"),
                    rs.getInt("rvSP"),
                    rs.getString("userNickName")
                ), selectReviewsParam);
    }

    public List<GetTrainersRes> selectTrainers(int gymIdx){
        String selectTrainersQuery = "SELECT * FROM trainer WHERE Gym_gymIdx = ?";
        int selectTrainersParam = gymIdx;
        return this.jdbcTemplate.query(selectTrainersQuery,
                (rs,rowNum) -> new GetTrainersRes(
                    rs.getString("trainerName"),
                    rs.getString("trainerCareer"),
                    rs.getString("trainerIntroduce"),
                    rs.getString("trainerImg")
                ), selectTrainersParam);
    }

    public List<GetImgRes> selectImgs(int gymIdx){
        String selectImgsQuery = "SELECT gymImg, facility FROM gymimg where Gym_gymIdx = ?";
        int selectImgsParam = gymIdx;
        return this.jdbcTemplate.query(selectImgsQuery,
                (rs,rowNum) -> new GetImgRes(
                    rs.getString("gymImg"),
                    rs.getInt("facility")
                ), selectImgsParam);
    }

    public int updateReview(int userIdx, int gymIdx, String rvContent, int rvSP){
        String updateReviewQuery = "INSERT INTO review (`User_userIdx`, `Gym_gymIdx`, `rvContent`, `rvSP`, `rvStatus`)\n"
        + "VALUES (?, ?, ?, ?, '1');";

        Object[] updateReviewParams = new Object[]{userIdx, gymIdx, rvContent, rvSP};
        return this.jdbcTemplate.update(updateReviewQuery, updateReviewParams);
    }
}
