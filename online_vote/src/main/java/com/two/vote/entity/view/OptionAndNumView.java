package com.two.vote.entity.view;

import com.two.vote.entity.Optionss;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
public class OptionAndNumView extends Optionss {
    private Integer num;
    private String tips;
    private String width;
}



