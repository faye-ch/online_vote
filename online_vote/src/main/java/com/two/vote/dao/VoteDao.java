package com.two.vote.dao;

import com.two.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteDao extends JpaRepository<Vote,Long> {

    @Query(value = "SELECT COUNT(*) FROM vote WHERE articleid=? AND optionid=?",nativeQuery = true)
    Integer getOptionVoteNum(long articleId, long optionId);

    @Query(value = "SELECT source ,COUNT(*) AS num FROM vote WHERE articleid=? GROUP BY source",nativeQuery = true)
    List<Object> findBySourceAndCount(long articleId);

    @Query(value = "SELECT * FROM vote WHERE ip=? AND articleid=?",nativeQuery = true)
    List<Vote> findByIpAndArticleid(String ip,long articleId);

    @Query(value = "SELECT source ,COUNT(*) AS num FROM vote WHERE articleid=? AND optionid = ? GROUP BY source",nativeQuery = true)
    List<Object> findBySourceAndOptionid(long articleId,long optionid);
}
