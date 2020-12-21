package com.two.vote.service.impl;

import com.two.vote.dao.ArticleDao;
import com.two.vote.dao.OptionssDao;
import com.two.vote.entity.Article;
import com.two.vote.entity.Optionss;
import com.two.vote.entity.view.ArticleAndOptionsView;
import com.two.vote.entity.view.ValueAndName;
import com.two.vote.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private OptionssDao optionssDao;

    @Override
    public List<Article> getManagerVoteList(long userId) {
        List<Article> articleList = articleDao.findByUserid(userId);
        return articleList;
    }

    @Override
    public void deleteArticleAndOptions(long articleId) {
        deleteArticleById(articleId);
        deleteOptionsByArticleId(articleId);
    }


    public void deleteArticleById(long articleId){
        articleDao.deleteById(articleId);
    }

    public void deleteOptionsByArticleId(long articleId){
        optionssDao.deleteOptionByArticleid(articleId);
    }


}
