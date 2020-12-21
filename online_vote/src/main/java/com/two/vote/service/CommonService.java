package com.two.vote.service;

import com.two.vote.entity.Article;
import com.two.vote.entity.Vote;
import com.two.vote.entity.view.*;

import java.util.List;

public interface CommonService {
    void addVote(UpArticleViewOptions upArticleViewOptions);

    List<ArticleAndFlag> getVoteList(String ip);

    ArticleAndOptionsView getVoteDetail(long id);

    Vote saveSubmitVote(Vote vote);

    ArticleAndOptionsView getCheckResulu(long articleid);

    List<ValueAndName> getPieView(long articleId);

    BarView getBarView(long articleId);

    List<ValueAndName> getSource(long articleId);

    boolean checkVoterIp(String ip,long articleId);

    Long checkVoted(String ip);

    List<ArticleAndFlag> search(String key,String ip);

    Article getArticle(Long articleid);

    void savecheckSubmitVote(List<Vote> voteList);

    ArticleAndOptionsView findAritleAndOptionById(long articleid);

    void addClickNum(long id);

}
