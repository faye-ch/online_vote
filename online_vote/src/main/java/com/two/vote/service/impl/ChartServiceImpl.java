package com.two.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.two.vote.dao.VoteDao;
import com.two.vote.entity.view.RangeAndVN;
import com.two.vote.entity.view.ValueAndName;
import com.two.vote.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private VoteDao voteDao;

    @Override
    public RangeAndVN getSourceByOption(long articleId, String optionid) {
        RangeAndVN rangeAndVN = new RangeAndVN();
        Integer min = new Integer(0);
        Integer max = new Integer(0);
        List<Object> bySourceAndCount = voteDao.findBySourceAndOptionid(articleId,Long.parseLong(optionid));
        List<ValueAndName> list = parseSourceResult(bySourceAndCount);
        for (ValueAndName valueAndName : list) {
            Integer value = valueAndName.getValue();
            if (value<min){
                min=value;
            }
            if (value>max){
                max=value;
            }
        }
        rangeAndVN.setMax(max);
        rangeAndVN.setMin(min);
        rangeAndVN.setValueAndNameList(list);
        return rangeAndVN;
    }

    @Override
    public RangeAndVN getSource(long articleId) {
        RangeAndVN rangeAndVN = new RangeAndVN();
        Integer min = new Integer(0);
        Integer max = new Integer(0);
        List<Object> bySourceAndCount = voteDao.findBySourceAndCount(articleId);
        List<ValueAndName> list = parseSourceResult(bySourceAndCount);
        for (ValueAndName valueAndName : list) {
            Integer value = valueAndName.getValue();
            if (value<min){
                min=value;
            }
            if (value>max){
                max=value;
            }
        }
        rangeAndVN.setMax(max);
        rangeAndVN.setMin(min);
        rangeAndVN.setValueAndNameList(list);
        return rangeAndVN;
    }


    public List<ValueAndName> parseSourceResult(List<Object> objectList){
        List<ValueAndName> list = new ArrayList<>();
        for (Object object : objectList) {
            ValueAndName valueAndName = new ValueAndName();
            String jsonString = JSON.toJSONString(object);
            String[] split = jsonString.split(",");
//            String valueString = split[1].substring(split[1].indexOf("]")-1,split[1].indexOf("]"));
            String valueString = split[1].replace("]", "");
            String nameString = split[0].substring(split[0].indexOf("\"")+1, split[0].lastIndexOf("省"));
            valueAndName.setValue(Integer.parseInt(valueString));
            valueAndName.setName(nameString);
            list.add(valueAndName);
        }
        return list;
    }


}
