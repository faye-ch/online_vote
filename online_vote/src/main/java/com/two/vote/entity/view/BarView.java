package com.two.vote.entity.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class BarView {
    private List<String> nameList;
    private List<Integer> valueList;
}
