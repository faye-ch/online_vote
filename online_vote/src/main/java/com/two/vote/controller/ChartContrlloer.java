package com.two.vote.controller;

import com.two.vote.entity.view.ArticleAndOptionsView;
import com.two.vote.entity.view.BarView;
import com.two.vote.entity.view.RangeAndVN;
import com.two.vote.entity.view.ValueAndName;
import com.two.vote.service.ChartService;
import com.two.vote.service.CommonService;
import com.two.vote.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChartContrlloer {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ChartService chartService;

    @Autowired
    private ManagerService managerService;

    @GetMapping("resultData/{id}")
    public ResponseEntity<ArticleAndOptionsView> getCheckResult(@PathVariable("id") long articleid){
        ArticleAndOptionsView articleAndOptionsView = commonService.getCheckResulu(articleid);
        if (articleAndOptionsView!=null){
            return ResponseEntity.ok(articleAndOptionsView);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("getPieView/{articleId}")
    public ResponseEntity<List<ValueAndName>> getPieView(@PathVariable("articleId")long articleId){
        List<ValueAndName> list =commonService.getPieView(articleId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("getBarView/{articleId}")
    public ResponseEntity<BarView> getBarView(@PathVariable("articleId") long articleId){
        BarView barView = commonService.getBarView(articleId);
        return ResponseEntity.ok(barView);
    }

    @GetMapping("getSource/{articleId}")
    public ResponseEntity<RangeAndVN> getSource(@PathVariable("articleId")long articleId, HttpServletRequest request){
        String optionid = request.getParameter("optionid");
        RangeAndVN rangeAndVN = null;
        if (optionid!=null&&!"".equals(optionid)){
            rangeAndVN = chartService.getSourceByOption(articleId,optionid);
        }else {
            rangeAndVN = chartService.getSource(articleId);
        }
        return ResponseEntity.ok(rangeAndVN);
    }
}
