package com.two.vote.controller;

import com.two.vote.entity.Article;
import com.two.vote.entity.Vote;
import com.two.vote.entity.view.ArticleAndFlag;
import com.two.vote.entity.view.ArticleAndOptionsView;
import com.two.vote.entity.view.UpArticleViewOptions;
import com.two.vote.service.CommonService;
import com.two.vote.service.ManagerService;
import com.two.vote.service.UploadSerivce;
import com.two.vote.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private ManagerService managerService;

    @PostMapping("/addVote")
    public String addVote(HttpServletRequest request, Model model, MultipartFile file){
        String fileName = uploadSerivce.upload(file);
        UpArticleViewOptions upArticleViewOptions = makeArticleViewOptions(request,fileName);
        commonService.addVote(upArticleViewOptions);
        String url = "http://localhost:8088/voteDetail/"+upArticleViewOptions.getId();
        model.addAttribute("shareUrl",url);
        return "tipPage/share";
    }

    @PostMapping("update")
    public String update(HttpServletRequest request,Model model,MultipartFile file){
        String fileName = uploadSerivce.upload(file);
        UpArticleViewOptions upArticleViewOptions = makeArticleViewOptions(request,fileName);
        managerService.deleteArticleAndOptions(upArticleViewOptions.getId());
        commonService.addVote(upArticleViewOptions);
        String url = "http://localhost:8088/voteDetail/"+upArticleViewOptions.getId();
        String msg = "修改成功！";
        model.addAttribute("shareUrl",url);
        model.addAttribute("msg",msg);
        return "tipPage/share";
    }

    @GetMapping("search")
    public ResponseEntity<List<ArticleAndFlag>> search(HttpServletRequest request){
        String key = request.getParameter("key");
        String ip = request.getParameter("ip");
        List<ArticleAndFlag> articleAndFlagList = commonService.search(key,ip);
        return ResponseEntity.ok(articleAndFlagList);
    }

    @GetMapping("/voteList/{ip}")
    public ResponseEntity<List<ArticleAndFlag>> getVoteList(@PathVariable("ip") String ip){
        if (ip==null&&"".equals(ip)){
            return ResponseEntity.notFound().build();
        }
        List<ArticleAndFlag> voteList = commonService.getVoteList(ip);
        return ResponseEntity.ok(voteList);
    }

    @GetMapping("/voteDetail/{id}")
    public String getVoteDetail(@PathVariable("id") long id, Model model,HttpServletRequest request){
        CommonUtil.setUserNameIdByCookie(request,model);
        commonService.addClickNum(id);
        ArticleAndOptionsView articleAndOptionsView = commonService.getVoteDetail(id);
        model.addAttribute("view",articleAndOptionsView);
        return "detail";
    }

    @PostMapping("submitVote")
    public String submitVote(HttpServletRequest request,Model model){
        String articleid = request.getParameter("articleid");
        String ipCity = request.getParameter("ipCity");
        String userid =(String)request.getSession().getAttribute("userid");
        userid="6";
        String[] split = ipCity.split(",");
        Vote vote = null;
        List<Vote> voteList = null;
        Article article = commonService.getArticle(Long.parseLong(articleid));
        Integer type = article.getType();
        boolean b = commonService.checkVoterIp(split[0],article.getId());
//        if (!b){
//            String msg = "你已经投过票了！";
//            model.addAttribute("msg",msg);
//            return "tipPage/voteError";
//        }
        if (type==0){
            vote=makeAcceptVote(request,null);
            commonService.saveSubmitVote(vote);
        }else {
            voteList = makeAcceptVoteList(request);
            commonService.savecheckSubmitVote(voteList);
        }
        String resultUrl = "http://localhost:8088/checkResult/"+article.getId();
        model.addAttribute("resultUrl",resultUrl);
        return "redirect:checkResult/"+article.getId();
    }

    private List<Vote> makeAcceptVoteList(HttpServletRequest request) {
        List<Vote> voteList = new ArrayList<>();
        String[] optionids = request.getParameterValues("optionid");
        for (String optionid : optionids) {
            Vote vote = makeAcceptVote(request, optionid);
            voteList.add(vote);
        }
        return voteList;
    }

    @GetMapping("checkVoted/{ip}")
    public ResponseEntity<Long> checkVoted(@PathVariable("ip") String ip){
        Long articleid = commonService.checkVoted(ip);
        return ResponseEntity.ok(articleid);
    }

    @GetMapping("checkResult/{id}")
    public String getCheckResult(@PathVariable("id") long articleid,Model model,HttpServletRequest request){
        CommonUtil.setUserNameIdByCookie(request,model);
        ArticleAndOptionsView articleAndOptionsView = commonService.getCheckResulu(articleid);
        if (articleAndOptionsView!=null){
            model.addAttribute("resultView",articleAndOptionsView);
            return "result";
        }
        model.addAttribute("msg","查看的投票不存在！");
        return "tipPage/voteError";
    }



    public Vote makeAcceptVote(HttpServletRequest request,String optionid){
        if (optionid==null){
            optionid = request.getParameter("optionid");
        }

        String articleid = request.getParameter("articleid");
        String ipCity = request.getParameter("ipCity");
        String[] split = ipCity.split(",");
        Vote vote = new Vote();
        vote.setOptionid(Long.parseLong(optionid));
        vote.setArticleid(Long.parseLong(articleid));
        vote.setVotetime(new Date());
        vote.setVoterid(6l);
        vote.setIp(split[0]);
        vote.setSource(split[1]);
        return vote;
    }





    public UpArticleViewOptions makeArticleViewOptions(HttpServletRequest request,String fileName){
        String articleid = request.getParameter("articleid");
        String title = request.getParameter("title");
        String[] options = request.getParameterValues("option");
        String type = request.getParameter("type");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String userid = null;
        if (fileName==null){
            fileName = request.getParameter("img");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userid")){
                userid=cookie.getValue();
            }
        }
        List<String> optionList = new ArrayList<>();
        for (String option : options) {
            optionList.add(option);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        UpArticleViewOptions upArticleViewOptions = new UpArticleViewOptions();
        try {
            if (articleid!=null&&!"".equals(articleid)){
                upArticleViewOptions.setId(Long.parseLong(articleid));
            }
            upArticleViewOptions.setTitle(title);
            upArticleViewOptions.setOptionList(optionList);
            upArticleViewOptions.setBegintime(simpleDateFormat.parse(starttime));
            upArticleViewOptions.setEndtime(simpleDateFormat.parse(endtime));
            upArticleViewOptions.setType(Integer.parseInt(type));
            upArticleViewOptions.setCreatetime(new Date());
            upArticleViewOptions.setImg(fileName);
            upArticleViewOptions.setUserid(Long.parseLong(userid));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return upArticleViewOptions;
    }
}
