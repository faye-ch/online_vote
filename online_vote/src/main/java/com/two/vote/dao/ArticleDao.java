package com.two.vote.dao;

import com.two.vote.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article,Long> {

    public List<Article> findByUserid(long userid);

    @Query(value = "SELECT * FROM article WHERE title LIKE ?",nativeQuery = true)
    List<Article> search(String key);
}
