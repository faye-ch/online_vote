package com.two.vote.service;

import com.two.vote.entity.Article;
import com.two.vote.entity.view.ArticleAndOptionsView;
import com.two.vote.entity.view.ValueAndName;
import org.springframework.ui.Model;

import java.util.List;

public interface ManagerService {
    List<Article> getManagerVoteList(long userId);

    void deleteArticleAndOptions(long articleId);



}
