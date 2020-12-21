package com.two.vote.entity.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class RangeAndVN {
    private Integer max;
    private Integer min;
    private List<ValueAndName> valueAndNameList;
}
