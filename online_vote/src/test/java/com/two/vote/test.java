package com.two.vote;

import com.two.vote.dao.ArticleDao;
import com.two.vote.dao.OptionssDao;
import com.two.vote.dao.VoteDao;
import com.two.vote.entity.Article;
import com.two.vote.entity.Optionss;
import com.two.vote.entity.Vote;
import com.two.vote.entity.view.ValueAndName;
import com.two.vote.service.impl.ChartServiceImpl;
import com.two.vote.utils.CreateIdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private OptionssDao optionssDao;

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private ChartServiceImpl chartService;

    @Test
    public void random(){
        Long aLong = CreateIdUtil.timeId();
        Long aLong1 = CreateIdUtil.timeId();

        System.out.println(aLong+" "+aLong1);
    }

    @Test
    public void timeId(){
        Date date = new Date();
        long time01 = date.getTime();
        long time02 = date.getTime();
        long time03 = System.currentTimeMillis();
        long time04 = System.currentTimeMillis();
        System.out.println(time01+" "+time02);
        System.out.println(time03+" "+time04);

        long time05 = System.currentTimeMillis();
        long time06 = System.currentTimeMillis();
        int random = (int)Math.random()*1000;
        int random01 = (int) Math.random()*1000;
        long id = time05+random;
        long id2 = time06+random01;
        System.out.println(id);
        System.out.println(id2);
    }

    @Test
    public void testArtilceDao(){
        List<Article> list = articleDao.findAll();
        for (Article article : list) {
            System.out.println(article.getTitle());
        }
    }

    @Test
    public void testFindByArticleId(){
        List<Optionss> list = optionssDao.findByArticleid(1603005990534l);
        for (Optionss optionss : list) {
            System.out.println(optionss.getOptionvalue());
        }
    }

    @Test
    public void findByUserIdTest(){
        List<Article> articleList = articleDao.findByUserid(7l);
        for (Article article : articleList) {
            System.out.println(article.getTitle());
        }
    }

    @Test
    public void deleteArticleAndOptions(){

        optionssDao.deleteOptionByArticleid(1603349043856l);
        optionssDao.deleteOptionByArticleid(1603349219740l);
        optionssDao.deleteOptionByArticleid(1603354642740l);
        optionssDao.deleteOptionByArticleid(1603440107396l);
        optionssDao.deleteOptionByArticleid(1603355150872l);
    }

    @Test
    public void findBySourceAndCount(){
        List<Object> bySourceAndCount = voteDao.findBySourceAndCount(1603005990534l);
        List<ValueAndName> list = chartService.parseSourceResult(bySourceAndCount);
        for (ValueAndName valueAndName : list) {
            System.out.println(valueAndName.getName()+valueAndName.getValue());

        }


    }

    @Test
    public void subStringTest(){
        String jsonString = "[\"广东省\",2]";
        String[] split = jsonString.split(",");
        int i = split[1].indexOf("]");
        String valueString = split[1].substring(split[1].indexOf("]")-1,split[1].indexOf("]"));
        String nameString = split[0].substring(split[0].indexOf("\"")+1, split[0].lastIndexOf("省"));

        System.out.println(valueString+" "+nameString);
    }

    @Test
    public void ipCitySubString(){
        String ipCityStr = "113.68.48.26,广东省广州市";
        String[] split = ipCityStr.split(",");
        for (String s : split) {
            System.out.println(s);
        }

    }

    @Test
    public void checkVoterIpTest(){
        List<Vote> byIpAndArticleid = voteDao.findByIpAndArticleid("113.68.48.26", 1603005990534l);
        for (Vote vote : byIpAndArticleid) {
            System.out.println(vote.getIp());
        }
    }

    @Test
    public void searchTest(){
        List<Article> search = articleDao.search("%大湾区%");
        for (Article article : search) {
            System.out.println(article.getTitle());
        }

    }

    @Test
    public void extTest(){
        String fileName = "java.jpg";
        String last = org.apache.commons.lang3.StringUtils.substringAfterLast(fileName, ".");
        System.out.println(last);
    }

    @Test
    public void fileNameTest(){
        String fileName = CreateIdUtil.randomFileName("java.jpg");
        System.out.println(fileName);
    }

    @Test
    public void getPath(){
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String target = path.substring(1,path.indexOf("online_vote"));
        System.out.println(target);
    }

    @Test
    public void getCurrentPath(){
        String currentPath = CreateIdUtil.getCurrentPath();
        System.out.println(currentPath);
    }


    public String getPercent(int total,int x){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)  x/ (float)total* 100);//所占百分比
        result = result+"%";
        return result;
    }

    @Test
    public void getPercentTest(){
        String percent = getPercent(10, 5);
        System.out.println(percent);
    }

    @Test
    public void TimeStringTest(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String string = format.format(date);
        System.out.println(string);

    }
    @Test
    public void string(){
         String [] split = {"广东","30]"};
//        String valueString = split[1].substring(split[1].indexOf("]")-1,split[1].indexOf("]"));
        String valueString = split[1].replace("]", "");
        System.out.println(valueString);
    }

}
