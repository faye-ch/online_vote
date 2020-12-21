package com.two.vote.service.impl;

import com.two.vote.dao.ArticleDao;
import com.two.vote.dao.OptionssDao;
import com.two.vote.dao.VoteDao;
import com.two.vote.entity.Article;
import com.two.vote.entity.Optionss;
import com.two.vote.entity.Vote;
import com.two.vote.entity.view.*;
import com.two.vote.service.CommonService;
import com.two.vote.utils.CreateIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private OptionssDao optionssDao;

    @Autowired
    private VoteDao voteDao;

    @Override
    public void addVote(UpArticleViewOptions upArticleViewOptions) {
        if (upArticleViewOptions.getId()==null){
            upArticleViewOptions.setId(CreateIdUtil.timeId());
        }
        Article article = makeArticle(upArticleViewOptions);
        List<Optionss> optionssList = makeOptionss(upArticleViewOptions);
        articleDao.save(article);
        for (Optionss optionss : optionssList) {
            optionssDao.save(optionss);
        }
    }

    //增加点击量
    @Override
    public void addClickNum(long id) {
        Optional<Article> optional = articleDao.findById(id);
        Article article = null;
        if (optional.isPresent()){
            article = optional.get();
            Long clickNum = article.getHot();
            if (clickNum==null){
                clickNum=0l;
            }
            clickNum++;
            article.setHot(clickNum);
            articleDao.save(article);
        }
    }

    @Override
    public void savecheckSubmitVote(List<Vote> voteList) {
        for (Vote vote : voteList) {
            voteDao.save(vote);
        }
    }

    @Override
    public List<ArticleAndFlag> search(String key,String ip) {
        String keys = "%"+key+"%";
        List<Article> articleList = articleDao.search(keys);
        List<ArticleAndFlag> articleAndFlagList = makeArticleAndFlags(articleList, ip);
        return articleAndFlagList;
    }

    @Override
    public Article getArticle(Long articleid) {
        Optional<Article> optional = articleDao.findById(articleid);
        return optional.get();
    }

    public List<ArticleAndFlag> makeArticleAndFlags(List<Article> articleList, String ip){
        List<ArticleAndFlag> articleAndFlagList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleAndFlag articleAndFlag = new ArticleAndFlag();
            BeanUtils.copyProperties(article,articleAndFlag);
            boolean flag = checkVoterIp(ip,article.getId());

            if (!flag){
                articleAndFlag.setFlag(1);
            }else {
                articleAndFlag.setFlag(0);
            }
            articleAndFlagList.add(articleAndFlag);
        }

        return articleAndFlagList;
    }

    @Override
    public List<ArticleAndFlag> getVoteList(String ip) {
        List<ArticleAndFlag> articleAndFlagList = new ArrayList<>();
        List<Article> all = articleDao.findAll();
        for (Article article : all) {
            ArticleAndFlag articleAndFlag = new ArticleAndFlag();
            Date endtime = article.getEndtime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String deadline = format.format(endtime);
            BeanUtils.copyProperties(article,articleAndFlag);
            articleAndFlag.setDeadline(deadline);
            boolean flag = checkVoterIp(ip,article.getId());
            if (!flag){
                articleAndFlag.setFlag(1);
            }else {
                articleAndFlag.setFlag(0);
            }
            articleAndFlagList.add(articleAndFlag);
        }

        return articleAndFlagList;
    }

    public void setClickStyle(List<ArticleAndFlag> articleAndFlagList){
        int total = 0;
        for (ArticleAndFlag articleAndFlag : articleAndFlagList) {
            total=articleAndFlagList.size();
            if (articleAndFlag.getHot()==null){
                articleAndFlag.setHot(0l);
            }

        }
    }


    public Article makeArticle(UpArticleViewOptions upArticleViewOptions){
        Article article = new Article();
        article.setId(upArticleViewOptions.getId());
        article.setTitle(upArticleViewOptions.getTitle());
        article.setType(upArticleViewOptions.getType());
        article.setCreatetime(upArticleViewOptions.getCreatetime());
        article.setBegintime(upArticleViewOptions.getBegintime());
        article.setEndtime(upArticleViewOptions.getEndtime());
        article.setImg(upArticleViewOptions.getImg());
        article.setUserid(upArticleViewOptions.getUserid());
        return article;
    }

    public List<Optionss> makeOptionss(UpArticleViewOptions upArticleViewOptions){
        List<Optionss> optionList = new ArrayList<>();
        for (String option : upArticleViewOptions.getOptionList()) {
            Optionss optionss = new Optionss();
            optionss.setArticleid(upArticleViewOptions.getId());
            optionss.setOptionvalue(option);
            optionList.add(optionss);
        }
        return optionList;
    }

    @Override
    public List<ValueAndName> getPieView(long articleId) {
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(articleId);
        List<OptionAndNumView> andNumViewList = articleAndOptionsView.getOptionAndNumViews();
        List<ValueAndName> valueAndNameList = new ArrayList<>();
        for (OptionAndNumView optionAndNumView : andNumViewList) {
            ValueAndName valueAndName = new ValueAndName();
            valueAndName.setValue(optionAndNumView.getNum());
            valueAndName.setName(optionAndNumView.getOptionvalue());
            valueAndNameList.add(valueAndName);
        }
        return valueAndNameList;
    }

    @Override
    public List<ValueAndName> getSource(long articleId) {
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(articleId);

        return null;
    }

    @Override
    public boolean checkVoterIp(String ip,long articleId) {
        List<Vote> byIpAndArticleid = voteDao.findByIpAndArticleid(ip,articleId);
        if (CollectionUtils.isEmpty(byIpAndArticleid))
        {
            return true;
        }
        return false;
    }

    @Override
    public BarView getBarView(long articleId) {
        BarView barView = new BarView();
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(articleId);
        List<OptionAndNumView> numViewList = articleAndOptionsView.getOptionAndNumViews();
        List<String> nameList = new ArrayList<>();
        List<Integer> valueList = new ArrayList<>();
        for (OptionAndNumView optionAndNumView : numViewList) {
            nameList.add(optionAndNumView.getOptionvalue());
            valueList.add(optionAndNumView.getNum());
        }
        barView.setNameList(nameList);
        barView.setValueList(valueList);
        return barView;
    }

    @Override
    public Long checkVoted(String ip) {
        return null;
    }

    @Override
    public ArticleAndOptionsView getVoteDetail(long id) {
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(id);
        return articleAndOptionsView;
    }

    @Override
    public ArticleAndOptionsView findAritleAndOptionById(long articleid) {
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(articleid);
        return articleAndOptionsView;
    }

    public ArticleAndOptionsView makeArticleAndOptionsView(long id){
        Optional<Article> optional = articleDao.findById(id);
        List<Optionss> optionssList = optionssDao.findByArticleid(id);
        ArticleAndOptionsView upArticleViewOptions = new ArticleAndOptionsView();
        if (optional.isPresent()){
            Article article = optional.get();
            upArticleViewOptions.setType(article.getType());
            upArticleViewOptions.setCreatetime(article.getCreatetime());
            upArticleViewOptions.setEndtime(article.getEndtime());
            upArticleViewOptions.setBegintime(article.getBegintime());
            upArticleViewOptions.setTitle(article.getTitle());
            upArticleViewOptions.setId(article.getId());
            upArticleViewOptions.setOptionList(optionssList);
            upArticleViewOptions.setImg(article.getImg());
            upArticleViewOptions.setHot(article.getHot());
            upArticleViewOptions.setUserid(article.getUserid());

            List<OptionAndNumView> optionAndNumViewList = getOptionAndNumViewList(optionssList);
            upArticleViewOptions.setOptionAndNumViews(optionAndNumViewList);
        }

        return upArticleViewOptions;
    }

    public List<OptionAndNumView> getOptionAndNumViewList(List<Optionss> optionssList){
        List<OptionAndNumView> list = new ArrayList<>();
        Integer sum = 0;
        for (Optionss optionss : optionssList) {
            Integer num = getOptionVoteNum(optionss.getArticleid(), optionss.getId());
            sum=sum+num;
        }
        for (Optionss optionss : optionssList) {
            OptionAndNumView optionAndNumView = new OptionAndNumView();
            Integer num = getOptionVoteNum(optionss.getArticleid(), optionss.getId());
            String percent = CreateIdUtil.getPercent(sum, num);

            optionAndNumView.setArticleid(optionss.getArticleid());
            optionAndNumView.setId(optionss.getId());
            optionAndNumView.setOptionvalue(optionss.getOptionvalue());
            optionAndNumView.setNum(num);
            optionAndNumView.setTips(percent);
            optionAndNumView.setWidth("width:"+percent);
            list.add(optionAndNumView);
        }
        return list;
    }

    public Integer getOptionVoteNum(long articleId,long optionId){
        Integer num = voteDao.getOptionVoteNum(articleId,optionId);
        return num;
    }

    @Override
    public Vote saveSubmitVote(Vote vote) {
        Vote save = voteDao.save(vote);
        return save;
    }

    @Override
    public ArticleAndOptionsView getCheckResulu(long articleid) {
        ArticleAndOptionsView articleAndOptionsView = makeArticleAndOptionsView(articleid);
        List<OptionAndNumView> list = articleAndOptionsView.getOptionAndNumViews();
        return articleAndOptionsView;
    }

}
