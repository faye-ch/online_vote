package com.two.vote.service;

import com.two.vote.entity.view.RangeAndVN;
import com.two.vote.entity.view.ValueAndName;

import java.util.List;

public interface ChartService {
    RangeAndVN getSource(long articleId);

    RangeAndVN getSourceByOption(long articleId, String optionid);
}
