package com.two.vote.dao;

import com.two.vote.entity.Optionss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OptionssDao extends JpaRepository<Optionss,Long> {

    public List<Optionss> findByArticleid(long articleid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM optionss WHERE articleid=?",nativeQuery = true)
    public void deleteOptionByArticleid(long articleid);
}
